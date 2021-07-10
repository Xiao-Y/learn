package com.billow.system.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.mybatis.utils.MybatisKet;
import com.billow.system.dao.PermissionDao;
import com.billow.system.dao.RolePermissionDao;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.service.PermissionService;
import com.billow.system.service.redis.RolePermissionRedisKit;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, PermissionPo> implements PermissionService {

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private RolePermissionRedisKit rolePermissionRedisKit;

    @Override
    public Set<PermissionPo> findPermissionByRole(RolePo rolePo) {

        Set<PermissionPo> permissionPos = new HashSet<>();

        // 查询权限信息
        LambdaQueryWrapper<RolePermissionPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RolePermissionPo::getRoleId, rolePo.getId())
                .eq(RolePermissionPo::getValidInd, true);
        List<RolePermissionPo> rolePermissionPos = rolePermissionDao.selectList(wrapper);
        if (CollectionUtils.isEmpty(rolePermissionPos)) {
            logger.warn("角色：{}，未分配权限！", rolePo.getRoleName());
            return permissionPos;
        }

        rolePermissionPos.stream().forEach(rp -> {
            PermissionPo permissionPo = permissionDao.selectById(rp.getPermissionId());
            if (permissionPo.getValidInd() == null || !permissionPo.getValidInd()) {
                permissionPo = null;
            }
            if (permissionPo == null) {
                logger.warn("角色：{}，permissionId:{},未查询到信息！", rolePo.getRoleName(), rp.getId());
                return;
            }
            PermissionPo po = ConvertUtils.convertIgnoreBase(permissionPo, PermissionPo.class);
            permissionPos.add(po);
        });

        return permissionPos;
    }

    @Override
    public IPage<PermissionVo> findPermissionList(PermissionVo permissionVo) {
        IPage<PermissionPo> page = new Page<>(permissionVo.getPageNo(), permissionVo.getPageSize());
        PermissionPo convert = ConvertUtils.convert(permissionVo, PermissionPo.class);
        QueryWrapper<PermissionPo> conditionLike = MybatisKet.getConditionLike(convert);
        conditionLike.orderByDesc("update_time");
        return this.page(page, conditionLike).convert(this::convertToPermissionVo);
    }

    private PermissionVo convertToPermissionVo(PermissionPo permissionPo) {
        PermissionVo convert = ConvertUtils.convert(permissionPo, PermissionVo.class);
        String systemModule = convert.getSystemModule();
        if (ToolsUtils.isNotEmpty(systemModule)) {
            ArrayList<String> list = CollectionUtil.newArrayList(systemModule.split(","));
            convert.setSystemModules(list);
        }
        return convert;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PermissionVo deletePermissionById(Long id) {
        PermissionPo permissionPo = permissionDao.selectById(id);
        permissionDao.deleteById(id);
        // redis：删除所有角色所持有的该权限
        rolePermissionRedisKit.deleteRolePermissionById(id);
        return ConvertUtils.convert(permissionPo, PermissionVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePermission(PermissionVo permissionVo) {
        PermissionPo convert = ConvertUtils.convert(permissionVo, PermissionPo.class);
        permissionDao.insert(convert);
        ConvertUtils.convert(convert, permissionVo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePermission(PermissionVo permissionVo) {
        this.updateById(permissionVo);
        // redis：通过权限 id 更新权限信息
        rolePermissionRedisKit.updatePermissionById(permissionVo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PermissionVo prohibitPermissionById(Long id) {
        PermissionPo permissionPo = permissionDao.selectById(id);
        permissionPo.setValidInd(false);
        permissionDao.updateById(permissionPo);
        // redis：删除所有角色所持有的该权限
        rolePermissionRedisKit.deleteRolePermissionById(id);
        return ConvertUtils.convert(permissionPo, PermissionVo.class);
    }

    @Override
    public List<PermissionVo> findPermissionAll() {

        LambdaQueryWrapper<PermissionPo> condition = Wrappers.lambdaQuery();
        condition.eq(PermissionPo::getValidInd, true);
        condition.orderByAsc(PermissionPo::getId);
        List<PermissionPo> permissionPos = permissionDao.selectList(condition);
        List<PermissionVo> permissionVos = new ArrayList<>();
        permissionPos.stream().forEach(f -> {
            permissionVos.add(this.convertToPermissionVo(f));
        });
        return permissionVos;
    }
}

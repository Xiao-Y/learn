package com.billow.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.common.utils.UserTools;
import com.billow.mybatis.utils.MybatisKet;
import com.billow.system.dao.PermissionDao;
import com.billow.system.dao.RolePermissionDao;
import com.billow.system.pojo.po.MenuPermissionPo;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.service.MenuPermissionService;
import com.billow.system.service.PermissionService;
import com.billow.system.service.redis.RolePermissionRedisKit;
import com.billow.tools.utlis.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 查询用户是否有权限
 *
 * @author liuyongtao
 * @create 2019-04-29 18:11
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionDao, PermissionPo> implements PermissionService
{

    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private MenuPermissionService menuPermissionService;
    @Autowired
    private RolePermissionRedisKit rolePermissionRedisKit;
    @Autowired
    private UserTools userTools;

    @Override
    public Set<PermissionPo> findPermissionByRole(RolePo rolePo)
    {

        Set<PermissionPo> permissionPos = new HashSet<>();

        // 查询权限信息
        LambdaQueryWrapper<RolePermissionPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(RolePermissionPo::getRoleId, rolePo.getId())
                .eq(RolePermissionPo::getValidInd, true);
        List<RolePermissionPo> rolePermissionPos = rolePermissionDao.selectList(wrapper);
        if (CollectionUtils.isEmpty(rolePermissionPos))
        {
            logger.warn("角色：{}，未分配权限！", rolePo.getRoleName());
            return permissionPos;
        }

        rolePermissionPos.stream().forEach(rp -> {
            PermissionPo permissionPo = permissionDao.selectById(rp.getPermissionId());
            if (permissionPo.getValidInd() == null || !permissionPo.getValidInd())
            {
                permissionPo = null;
            }
            if (permissionPo == null)
            {
                logger.warn("角色：{}，permissionId:{},未查询到信息！", rolePo.getRoleName(), rp.getId());
                return;
            }
            PermissionPo po = ConvertUtils.convertIgnoreBase(permissionPo, PermissionPo.class);
            permissionPos.add(po);
        });

        return permissionPos;
    }

    @Override
    public IPage<PermissionVo> findPermissionList(PermissionVo permissionVo)
    {
        IPage<PermissionPo> page = new Page<>(permissionVo.getPageNo(), permissionVo.getPageSize());
        PermissionPo convert = ConvertUtils.convert(permissionVo, PermissionPo.class);
        QueryWrapper<PermissionPo> conditionLike = MybatisKet.getConditionLike(convert);
        conditionLike.orderByDesc("update_time");
        return this.page(page, conditionLike).convert(this::convertToPermissionVo);
    }

    private PermissionVo convertToPermissionVo(PermissionPo permissionPo)
    {
        PermissionVo convert = ConvertUtils.convert(permissionPo, PermissionVo.class);
//        String systemModule = convert.getSystemModule();
//        if (ToolsUtils.isNotEmpty(systemModule))
//        {
//            ArrayList<String> list = CollectionUtil.newArrayList(systemModule.split(","));
//            convert.setSystemModules(list);
//        }
        return convert;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PermissionVo deletePermissionById(Long id)
    {
        PermissionPo permissionPo = permissionDao.selectById(id);
        permissionDao.deleteById(id);
        // redis：删除所有角色所持有的该权限
        rolePermissionRedisKit.deleteRolePermissionById(id);
        return ConvertUtils.convert(permissionPo, PermissionVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void savePermission(PermissionVo permissionVo)
    {
        PermissionPo convert = ConvertUtils.convert(permissionVo, PermissionPo.class);
        permissionDao.insert(convert);
        this.saveMenuPermission(convert.getId(), permissionVo.getMenuIds());
        ConvertUtils.convert(convert, permissionVo);
    }

    /**
     * 保存菜单与权限关系
     *
     * @param permissionId 权限ID
     * @param menuIds      菜单IDs
     * @return void
     * @author 千面
     * @date 2022/1/7 14:15
     */
    private void saveMenuPermission(Long permissionId, List<Long> menuIds)
    {
        if (CollectionUtils.isNotEmpty(menuIds))
        {
            Set<MenuPermissionPo> menuPermissionPos = menuIds.stream()
                    .map(m -> {
                        MenuPermissionPo mp = new MenuPermissionPo();
                        mp.setPermissionId(permissionId);
                        mp.setMenuId(m);
                        return mp;
                    })
                    .collect(Collectors.toSet());
            menuPermissionService.saveBatch(menuPermissionPos);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void updatePermission(PermissionVo permissionVo)
    {
        this.updateById(permissionVo);
        // 先删除菜单与权限关系，再重新绑定
        LambdaQueryWrapper<MenuPermissionPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(MenuPermissionPo::getPermissionId, permissionVo.getId());
        menuPermissionService.remove(wrapper);
        this.saveMenuPermission(permissionVo.getId(), permissionVo.getMenuIds());
        // redis：通过权限 id 更新权限信息
        rolePermissionRedisKit.updatePermissionById(permissionVo);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PermissionVo prohibitPermissionById(Long id)
    {
        PermissionPo permissionPo = permissionDao.selectById(id);
        permissionPo.setValidInd(false);
        permissionDao.updateById(permissionPo);
        // redis：删除所有角色所持有的该权限
        rolePermissionRedisKit.deleteRolePermissionById(id);
        return ConvertUtils.convert(permissionPo, PermissionVo.class);
    }

    @Override
    public List<PermissionVo> findPermissionAll()
    {
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

    @Override
    public List<String> findMyPermissionList()
    {
        List<String> roleCodeList = userTools.getCurrentRoleCode();
        if (CollectionUtils.isEmpty(roleCodeList))
        {
            return new ArrayList<>();
        }

        return roleCodeList.stream()
                .map(m -> getRolePermissionByRoleCode(m))
                .flatMap(Collection::stream)
                .map(PermissionPo::getPermissionCode)
                .filter(StringUtils::isNoneBlank)
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionVo> findPermissionByMenuId(Long menuId)
    {
        List<PermissionPo> permissionPos = permissionDao.findPermissionByMenuId(menuId);
        return BeanUtil.copyToList(permissionPos, PermissionVo.class);
    }

    /**
     * 通过 roleCode 获取权限信息
     *
     * @param m
     * @return List<PermissionVo>
     * @author 千面
     * @date 2021/12/18 16:30
     */
    private List<PermissionVo> getRolePermissionByRoleCode(String m)
    {
        List<PermissionVo> temp = rolePermissionRedisKit.getRolePermissionByRoleCode(m);
        if (CollectionUtils.isEmpty(temp))
        {
            List<PermissionPo> permissionPos = permissionDao.findPermissionByRoleCode(Arrays.asList(m));
            if (CollectionUtils.isEmpty(permissionPos))
            {
                return new ArrayList<>();
            }
            rolePermissionRedisKit.setRolePermission(permissionPos, m);
            temp = ConvertUtils.convert(permissionPos, PermissionVo.class);
        }
        return temp;
    }
}

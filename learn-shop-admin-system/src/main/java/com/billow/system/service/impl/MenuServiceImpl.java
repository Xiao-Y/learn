package com.billow.system.service.impl;

import com.billow.system.dao.PermissionDao;
import com.billow.system.dao.RolePermissionDao;

import com.billow.system.pojo.ex.MenuEx;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.system.pojo.vo.PermissionVo;
import com.billow.system.pojo.vo.RoleVo;
import com.billow.system.service.MenuService;
import com.billow.tools.utlis.FieldUtils;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 菜单服务
 *
 * @author liuyongtao
 * @create 2018-05-26 9:54
 */
@Service
@Transactional(readOnly = true)
public class MenuServiceImpl implements MenuService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public List<MenuEx> homeMenus(PermissionVo permissionVo) {
        // 查询出该用户所有角色的所有的权限
        List<RoleVo> roleVos = permissionVo.getRoleVos();
        Set<Long> permissionIds = new HashSet<>();
        if (ToolsUtils.isNotEmpty(roleVos)) {
            roleVos.forEach(item -> {
                List<RolePermissionPo> rolePermissionPos = rolePermissionDao.findByRoleIdIsAndValidIndIsTrue(item.getId());
                if (ToolsUtils.isNotEmpty(rolePermissionPos)) {
                    rolePermissionPos.stream().forEach(rolePermissionPo -> permissionIds.add(rolePermissionPo.getPermissionId()));
                }
            });
        }
        // TODO 测试用
        if ("admin".equals(permissionVo.getUserCode())) {
            List<PermissionPo> all = permissionDao.findAll();
            if (ToolsUtils.isNotEmpty(all)) {
                Set<Long> set = all.stream().map(m -> m.getId()).collect(Collectors.toSet());
                permissionIds.clear();
                permissionIds.addAll(set);
            }
        }
        // 如果没有权限直接返回
        if (ToolsUtils.isEmpty(permissionIds)) {
            return null;
        }
        // 查询父级菜单
        List<PermissionPo> permissionPos = permissionDao.findByPidIsNullAndValidIndIsTrue();

        // 转换父级菜单
        List<MenuEx> pMenuExs = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(permissionPos)) {
            this.permissionPosCoverMenuExs(permissionPos, pMenuExs, permissionIds);
            // 递归查询子级菜单
            this.childenMenus(pMenuExs, permissionIds);
        }
        return pMenuExs;
    }

    @Override
    public List<MenuEx> findMenus() {
        // 查询父级菜单
        List<PermissionPo> permissionPos = permissionDao.findByPidIsNull();

        // 转换父级菜单
        List<MenuEx> pMenuExs = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(permissionPos)) {
            this.permissionPosCoverMenuExs(permissionPos, pMenuExs, null);
            // 递归查询子级菜单
            this.childenMenus(pMenuExs, null);
        }
        return pMenuExs;
    }

    @Override
    public PermissionVo findMenuById(Long id) {
        PermissionPo permissionPo = permissionDao.findOne(id);
        return ConvertUtils.convert(permissionPo, PermissionVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public PermissionVo saveOrUpdateMenu(PermissionVo permissionVo) throws Exception {
        Long id = permissionVo.getId();
        PermissionPo one;
        if (null != id) {
            one = permissionDao.findOne(id);
            ConvertUtils.copyNonNullProperties(permissionVo, one);
            one.setUpdateTime(new Date());
        } else {
            one = ConvertUtils.convert(permissionVo, PermissionPo.class);
            FieldUtils.setCommonFieldByInsertWithValidInd(one, permissionVo.getUpdaterCode());
        }
        permissionDao.save(one);

        return ConvertUtils.convert(one, PermissionVo.class);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void delMenuByIds(Set<String> ids) {
        ids.forEach(id -> {
            List<RolePermissionPo> rolePermissionPos = rolePermissionDao.findByPermissionId(new Long(id));
            if (ToolsUtils.isNotEmpty(rolePermissionPos)) {
                rolePermissionDao.deleteByPermissionId(new Long(id));
            }

            PermissionPo permissionPo = permissionDao.findOne(new Long(id));
            if (null != permissionPo) {
                permissionDao.delete(new Long(id));
            }
        });
    }

    /**
     * 递归查询子级菜单
     *
     * @param pMenuExs
     * @param permissionIds 用户所拥有的权限
     * @return void
     * @author LiuYongTao
     * @date 2018/5/29 14:48
     */
    private void childenMenus(List<MenuEx> pMenuExs, Set<Long> permissionIds) {
        pMenuExs.forEach(pitem -> {
            // 查询子级菜单
            List<PermissionPo> permissionPos;
            if (pitem.getValidInd() != null && pitem.getValidInd()) {
                permissionPos = permissionDao.findByPidEqualsAndValidIndIsTrue(new Long(pitem.getId()));
            } else {
                permissionPos = permissionDao.findByPidEquals(new Long(pitem.getId()));
            }

            if (ToolsUtils.isNotEmpty(permissionPos)) {
                // 转换子级菜单
                List<MenuEx> cMenuExs = new ArrayList<>();
                this.permissionPosCoverMenuExs(permissionPos, cMenuExs, permissionIds);
                pitem.setChildren(cMenuExs);
                // 递归查询子级菜单
                this.childenMenus(cMenuExs, permissionIds);
            }
        });
    }

    /**
     * PermissionPos 转换成 MenuExs
     *
     * @param permissionPos
     * @param pMenuExs
     * @param permissionIds 用户所拥有的权限
     * @return void
     * @author LiuYongTao
     * @date 2018/5/29 14:55
     */
    private void permissionPosCoverMenuExs(List<PermissionPo> permissionPos, List<MenuEx> pMenuExs, Set<Long> permissionIds) {
        permissionPos.forEach(item -> {
            // 如果没有权限直接抛弃
            if (permissionIds != null && !permissionIds.contains(item.getId())) {
                return;
            }

            MenuEx ex = new MenuEx();
            ex.setId(item.getId().toString())
                    .setPath(item.getUrl())
                    .setValidInd(item.getValidInd())
                    .setIcon(item.getIcon())
                    .setPid(item.getPid())
                    .setDisplay(item.getDisplay())
                    .setTitleCode(item.getPermissionCode())
                    .setTitle(item.getPermissionName());
            pMenuExs.add(ex);
        });
    }
}

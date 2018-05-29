package com.billow.service.impl;

import com.billow.dao.PermissionDao;
import com.billow.dao.RolePermissionDao;
import com.billow.pojo.ex.MenuEx;
import com.billow.pojo.po.relation.RolePermissionPo;
import com.billow.pojo.po.sys.PermissionPo;
import com.billow.pojo.vo.sys.PermissionVo;
import com.billow.pojo.vo.sys.RoleVo;
import com.billow.service.MenuService;
import com.billow.tools.utlis.PageUtil;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 菜单服务
 *
 * @author liuyongtao
 * @create 2018-05-26 9:54
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    @Override
    public List<MenuEx> indexMenus(PermissionVo permissionVo) {
        // 查询出该用户所有角色的所有的权限
        List<RoleVo> roleVos = permissionVo.getRoleVos();
        Set<Long> permissionIds = new HashSet<>();
        if (ToolsUtils.isNotEmpty(roleVos)) {
            roleVos.forEach(item -> {
                List<RolePermissionPo> rolePermissionPos = rolePermissionDao.findByRoleIdIsAndValidIndIsTrue(item.getId());
                if (ToolsUtils.isNotEmpty(rolePermissionPos)) {
                    rolePermissionPos.forEach(rolePermissionPo -> permissionIds.add(rolePermissionPo.getPermissionId()));
                }
            });
        }
        // 如果没有权限直接返回
        if (ToolsUtils.isEmpty(permissionIds)) {
            return null;
        }
        // 查询父级菜单
        List<PermissionPo> permissionPos;
        if (permissionVo.getValidInd()) {
            permissionPos = permissionDao.findByPidIsNullAndValidIndIsTrue();
        } else {
            permissionPos = permissionDao.findByPidIsNull();
        }

        // 转换父级菜单
        List<MenuEx> pMenuExs = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(permissionPos)) {
            this.permissionPosCoverMenuExs(permissionPos, pMenuExs, permissionIds);
            // 递归查询子级菜单
            this.childenMenus(pMenuExs, permissionIds);
        }
        return pMenuExs;
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
            if (pitem.getValidInd()) {
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
            if (!permissionIds.contains(item.getId())) {
                return;
            }

            MenuEx ex = new MenuEx();
            ex.setId(item.getId().toString())
                    .setPath(item.getUrl())
                    .setValidInd(item.getValidInd())
                    .setIcon(item.getIcon())
                    .setTitle(item.getPermissionCode());
            pMenuExs.add(ex);
        });
    }
}

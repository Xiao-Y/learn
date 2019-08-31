package com.billow.system.dao;

import com.billow.system.pojo.po.RoleMenuPo;
import com.billow.system.pojo.po.RolePermissionPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleMenuDao extends JpaRepository<RoleMenuPo, Long> {

    /**
     * roleId查询出有效的关联信息
     *
     * @param roleId
     * @return
     */
    List<RoleMenuPo> findByRoleIdIsAndValidIndIsTrue(Long roleId);

    /**
     * 通过 menuId 删除相应关联
     *
     * @param menuId
     */
    void deleteByMenuId(Long menuId);

    /**
     * 通过 menuId 查询相应关联
     *
     * @param menuId
     */
    List<RoleMenuPo> findByMenuId(Long menuId);

    /**
     * 删除该角色的菜单
     *
     * @param roleId
     * @return void
     * @author LiuYongTao
     * @date 2019/7/30 10:18
     */
    void deleteByRoleId(Long roleId);

    /**
     * roleId查询出有效的关联信息
     *
     * @param id
     * @return java.util.List<com.billow.system.pojo.po.RoleMenuPo>
     * @author LiuYongTao
     * @date 2019/7/30 10:59
     */
    List<RoleMenuPo> findByRoleId(Long id);

    /**
     * 删除 roleId 角色的指定菜单信息
     *
     * @param roleId 角色id
     * @param menus  需要删除的菜单id 集合
     * @return void
     * @author billow
     * @date 2019/8/31 11:12
     */
    void deleteByRoleIdAndMenuIdIn(Long roleId, List<Long> menus);
}

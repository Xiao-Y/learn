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
}

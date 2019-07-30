package com.billow.system.dao;

import com.billow.system.pojo.po.RolePermissionPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionDao extends JpaRepository<RolePermissionPo, Long> {

    /**
     * roleId查询出有效的关联信息
     *
     * @param roleId
     * @return
     */
    List<RolePermissionPo> findByRoleIdIsAndValidIndIsTrue(Long roleId);

//    /**
//     * 通过permissionId 删除相应关联
//     *
//     * @param permissionId
//     */
//    void deleteByPermissionId(Long permissionId);
//
//    /**
//     * 通过permissionId 查询相应关联
//     *
//     * @param permissionId
//     */
//    List<RolePermissionPo> findByPermissionId(Long permissionId);

    /**
     * 删除该角色的权限
     *
     * @param roleId
     * @return void
     * @author LiuYongTao
     * @date 2019/7/30 10:16
     */
    void deleteByRoleId(Long roleId);

    /**
     * 删除原始的关联权限数据
     *
     * @param id
     * @return java.util.List<com.billow.system.pojo.po.RolePermissionPo>
     * @author LiuYongTao
     * @date 2019/7/30 10:59
     */
    List<RolePermissionPo> findByRoleId(Long id);
}

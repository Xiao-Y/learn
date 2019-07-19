//package com.billow.auth.dao;
//
//import com.billow.auth.pojo.po.RolePermissionPo;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
//public interface RolePermissionDao extends JpaRepository<RolePermissionPo, Long> {
//
//    /**
//     * roleId查询出有效的关联信息
//     *
//     * @param roleId
//     * @return
//     */
//    List<RolePermissionPo> findByRoleIdIsAndValidIndIsTrue(Long roleId);
//
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
//}

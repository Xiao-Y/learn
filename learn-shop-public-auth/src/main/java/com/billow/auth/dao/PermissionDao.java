//package com.billow.auth.dao;
//
//import com.billow.auth.pojo.po.PermissionPo;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
//
//import java.util.List;
//import java.util.Optional;
//
//public interface PermissionDao extends JpaRepository<PermissionPo, Long>, JpaSpecificationExecutor<PermissionPo> {
//
//    Optional<PermissionPo> findByIdAndValidIndIsTrue(Long permissionId);
//}

package com.billow.dao;

import com.billow.pojo.po.relation.RolePermissionPo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RolePermissionDao extends JpaRepository<RolePermissionPo, Long> {

    List<RolePermissionPo> findByRoleIdIsAndValidIndIsTrue(Long roleId);
}

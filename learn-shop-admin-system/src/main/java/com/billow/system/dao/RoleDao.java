package com.billow.system.dao;

import com.billow.system.pojo.po.RolePo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleDao extends JpaRepository<RolePo, Long>,JpaSpecificationExecutor<RolePo> {
}

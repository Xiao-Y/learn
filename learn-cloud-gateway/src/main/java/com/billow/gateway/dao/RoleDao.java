package com.billow.gateway.dao;

import com.billow.gateway.pojo.po.RolePo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<RolePo, Long> {
}

package com.billow.auth.dao;

import com.billow.auth.pojo.po.RolePo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<RolePo, Long> {
}

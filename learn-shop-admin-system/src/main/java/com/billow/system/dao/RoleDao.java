package com.billow.system.dao;

import com.billow.system.pojo.po.RolePo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<RolePo, Long> {
}

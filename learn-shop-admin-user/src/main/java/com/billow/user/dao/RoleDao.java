package com.billow.user.dao;

import com.billow.user.pojo.po.RolePo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleDao extends JpaRepository<RolePo, Long> {
}

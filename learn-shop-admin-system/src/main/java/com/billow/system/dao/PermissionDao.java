package com.billow.system.dao;

import com.billow.system.pojo.po.PermissionPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionDao extends JpaRepository<PermissionPo, Long> {

}

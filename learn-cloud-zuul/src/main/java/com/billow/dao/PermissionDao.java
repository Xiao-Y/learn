package com.billow.dao;

import com.billow.pojo.po.sys.PermissionPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PermissionDao extends JpaRepository<PermissionPo, Long> {

//    List<PermissionPo> findByUserId(int userId);
}

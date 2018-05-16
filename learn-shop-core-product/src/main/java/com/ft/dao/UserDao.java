package com.ft.dao;

import com.ft.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserPo, Long> {
}

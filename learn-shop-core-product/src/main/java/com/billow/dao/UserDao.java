package com.billow.dao;

import com.billow.pojo.po.user.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserPo, Long> {
}

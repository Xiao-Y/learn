package com.billow.auth.dao;

import com.billow.auth.pojo.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<UserPo, Long> {

    /**
     * 通过用户code 查询用户信息
     *
     * @param userCode 用户code
     * @return UserPo
     * @author LiuYongTao
     * @date 2018/11/5 16:09
     */
    UserPo findUserInfoByUsercodeAndValidIndIsTrue(String userCode);

    UserPo findUserInfoByUsercode(String userCode);
}
package com.billow.user.dao;

import com.billow.user.pojo.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<UserPo, Long>, JpaSpecificationExecutor<UserPo> {

//    /**
//     * 通过用户code 查询用户信息
//     *
//     * @param userCode 用户code
//     * @return UserPo
//     * @author LiuYongTao
//     * @date 2018/11/5 16:09
//     */
//    UserPo findUserInfoByUsercode(String userCode);
}
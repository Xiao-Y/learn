package com.billow.user.dao;

import com.billow.user.pojo.po.UserPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserDao extends JpaRepository<UserPo, Long>, JpaSpecificationExecutor<UserPo> {

    /**
     * 通过用户code 查询用户信息
     *
     * @param userCode
     * @return com.billow.user.pojo.po.UserPo
     * @author LiuYongTao
     * @date 2019/8/2 14:21
     */
    UserPo findByUsercode(String userCode);
}
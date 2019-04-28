package com.billow.user.service;

import com.billow.user.pojo.vo.UserVo;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserService {

    /**
     * 根据userCode 查询出用户信息
     *
     * @param [userCode]
     * @return UserVo
     * @author LiuYongTao
     * @date 2018/11/5 16:04
     */
    UserVo findUserInfoByUsercode(String userCode);

    UserDetails loadUserByUsername(String userCode);
}

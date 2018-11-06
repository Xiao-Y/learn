package com.billow.user.service;

import com.billow.user.pojo.vo.UserVo;

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
}

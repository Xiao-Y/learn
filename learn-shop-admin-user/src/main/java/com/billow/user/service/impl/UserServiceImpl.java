package com.billow.user.service.impl;

import com.billow.user.dao.UserDao;
import com.billow.user.pojo.po.UserPo;
import com.billow.user.pojo.vo.UserVo;
import com.billow.user.service.UserService;
import com.billow.tools.utlis.ConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户信息操作
 *
 * @author liuyongtao
 * @create 2018-11-05 15:28
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public UserVo findUserInfoByUsercode(String userCode) {
        UserPo userPo = userDao.findUserInfoByUsercode(userCode);
        UserVo userVo = ConvertUtils.convert(userPo, UserVo.class);
        return userVo;
    }
}

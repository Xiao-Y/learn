package com.ft.service.impl;

import com.ft.dao.UserDao;
import com.ft.po.UserPo;
import com.ft.service.UserService;
import com.ft.utlis.FieldUtils;
import com.ft.utlis.PageUtil;
import com.ft.vo.UserVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 测试
 *
 * @author liuyongtao
 * @create 2018-05-16 10:27
 */
@Service
public class UserServiceIpml implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void saveUser(UserVo userVo) throws Exception {
        UserPo userPo = PageUtil.convert(userVo, UserPo.class);
        FieldUtils.setCommonFieldByInsertWithValidInd(userPo, "admin");
        userDao.save(userPo);
    }
}

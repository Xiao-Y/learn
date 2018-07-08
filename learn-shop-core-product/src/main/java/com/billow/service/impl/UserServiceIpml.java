package com.billow.service.impl;

import com.billow.dao.UserDao;
import com.billow.pojo.po.user.UserPo;
import com.billow.service.UserService;
import com.billow.tools.utlis.FieldUtils;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.pojo.vo.user.UserVo;
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
        UserPo userPo = ConvertUtils.convert(userVo, UserPo.class);
        FieldUtils.setCommonFieldByInsertWithValidInd(userPo, "admin");
        userDao.save(userPo);
    }
}

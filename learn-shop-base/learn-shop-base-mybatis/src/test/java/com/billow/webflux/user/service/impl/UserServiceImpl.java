package com.billow.webflux.user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.webflux.user.dao.UserDao;
import com.billow.webflux.user.pojo.search.UserSearchParam;
import com.billow.webflux.user.pojo.po.UserPo;
import com.billow.webflux.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-12-14
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserPo> implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public IPage<UserPo> findListByPage(UserSearchParam userSearchParam) {
        IPage<UserPo> page = new Page<>(userSearchParam.getPageNo(), userSearchParam.getPageSize());
        LambdaQueryWrapper<UserPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<UserPo> selectPage = userDao.selectPage(page, wrapper);
        // 查询总条数
        Integer total = userDao.selectCount(wrapper);
        selectPage.setTotal(total);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        UserPo po = new UserPo();
        po.setValidInd(false);
        LambdaQueryWrapper<UserPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(UserPo::getId, id);
        return userDao.update(po, wrapper) >= 1;
    }
}


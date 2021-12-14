package com.billow.webflux.user.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.webflux.user.dao.UserDao;
import com.billow.webflux.user.pojo.search.UserSearchParam;
import com.billow.webflux.user.pojo.po.UserPo;
import com.billow.webflux.user.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-12-14
 */
@Service
public class UserServiceImpl extends HighLevelServiceImpl<UserDao, UserPo,UserSearchParam> implements UserService {

}


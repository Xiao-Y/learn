package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.UserDao;
import com.billow.system.pojo.search.UserSearchParam;
import com.billow.system.pojo.po.UserPo;
import com.billow.system.service.UserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2025-10-19
 */
@Service
public class UserServiceImpl extends HighLevelServiceImpl<UserDao, UserPo,UserSearchParam> implements UserService {

}


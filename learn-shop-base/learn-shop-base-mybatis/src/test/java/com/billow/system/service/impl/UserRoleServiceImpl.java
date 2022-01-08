package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.UserRoleDao;
import com.billow.system.pojo.search.UserRoleSearchParam;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.system.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2022-01-04
 */
@Service
public class UserRoleServiceImpl extends HighLevelServiceImpl<UserRoleDao, UserRolePo,UserRoleSearchParam> implements UserRoleService {

}


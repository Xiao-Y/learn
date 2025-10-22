package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.UserRoleDao;
import com.billow.system.pojo.po.UserRolePo;
import com.billow.system.pojo.search.UserRoleSearchParam;
import com.billow.system.service.UserRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 用户信息操作
 *
 * @author liuyongtao
 * @create 2018-11-05 15:28
 */
@Slf4j
@Service
public class UserRoleServiceImpl extends HighLevelServiceImpl<UserRoleDao, UserRolePo, UserRoleSearchParam> implements UserRoleService {

}

package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.RoleDao;
import com.billow.system.pojo.search.RoleSearchParam;
import com.billow.system.pojo.po.RolePo;
import com.billow.system.service.RoleService;
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
public class RoleServiceImpl extends HighLevelServiceImpl<RoleDao, RolePo,RoleSearchParam> implements RoleService {

}


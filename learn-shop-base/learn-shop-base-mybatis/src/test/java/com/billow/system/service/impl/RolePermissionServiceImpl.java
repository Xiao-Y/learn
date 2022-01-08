package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.RolePermissionDao;
import com.billow.system.pojo.search.RolePermissionSearchParam;
import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.system.service.RolePermissionService;
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
public class RolePermissionServiceImpl extends HighLevelServiceImpl<RolePermissionDao, RolePermissionPo,RolePermissionSearchParam> implements RolePermissionService {

}


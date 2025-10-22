package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.RolePermissionDao;
import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.system.pojo.search.RolePermissionSearchParam;
import com.billow.system.service.RolePermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-04-01
 */
@Service
public class RolePermissionServiceImpl extends HighLevelServiceImpl<RolePermissionDao, RolePermissionPo, RolePermissionSearchParam> implements RolePermissionService {

}


package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.PermissionDao;
import com.billow.system.pojo.search.PermissionSearchParam;
import com.billow.system.pojo.po.PermissionPo;
import com.billow.system.service.PermissionService;
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
public class PermissionServiceImpl extends HighLevelServiceImpl<PermissionDao, PermissionPo,PermissionSearchParam> implements PermissionService {

}


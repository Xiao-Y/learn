package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.MenuPermissionDao;
import com.billow.system.pojo.search.MenuPermissionSearchParam;
import com.billow.system.pojo.po.MenuPermissionPo;
import com.billow.system.service.MenuPermissionService;
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
public class MenuPermissionServiceImpl extends HighLevelServiceImpl<MenuPermissionDao, MenuPermissionPo,MenuPermissionSearchParam> implements MenuPermissionService {

}


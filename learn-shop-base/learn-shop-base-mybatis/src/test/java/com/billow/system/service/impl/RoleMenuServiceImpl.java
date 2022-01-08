package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.RoleMenuDao;
import com.billow.system.pojo.search.RoleMenuSearchParam;
import com.billow.system.pojo.po.RoleMenuPo;
import com.billow.system.service.RoleMenuService;
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
public class RoleMenuServiceImpl extends HighLevelServiceImpl<RoleMenuDao, RoleMenuPo,RoleMenuSearchParam> implements RoleMenuService {

}


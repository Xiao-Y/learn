package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.MenuDao;
import com.billow.system.pojo.search.MenuSearchParam;
import com.billow.system.pojo.po.MenuPo;
import com.billow.system.service.MenuService;
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
public class MenuServiceImpl extends HighLevelServiceImpl<MenuDao, MenuPo,MenuSearchParam> implements MenuService {

}


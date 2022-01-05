package com.billow.system.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.system.dao.WhiteListDao;
import com.billow.system.pojo.search.WhiteListSearchParam;
import com.billow.system.pojo.po.WhiteListPo;
import com.billow.system.service.WhiteListService;
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
public class WhiteListServiceImpl extends HighLevelServiceImpl<WhiteListDao, WhiteListPo,WhiteListSearchParam> implements WhiteListService {

}


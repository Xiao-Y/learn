package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.OrderSettingDao;
import com.billow.order.pojo.po.OrderSettingPo;
import com.billow.order.pojo.search.OrderSettingSearchParam;
import com.billow.order.service.OrderSettingService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@Service
public class OrderSettingServiceImpl extends HighLevelServiceImpl<OrderSettingDao, OrderSettingPo,OrderSettingSearchParam> implements OrderSettingService {

}


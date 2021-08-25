package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.OrderDao;
import com.billow.order.pojo.po.OrderPo;
import com.billow.order.pojo.search.OrderSearchParam;
import com.billow.order.service.OrderService;
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
public class OrderServiceImpl extends HighLevelServiceImpl<OrderDao, OrderPo,OrderSearchParam> implements OrderService {

}


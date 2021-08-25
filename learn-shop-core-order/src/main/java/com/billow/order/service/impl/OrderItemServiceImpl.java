package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.OrderItemDao;
import com.billow.order.pojo.po.OrderItemPo;
import com.billow.order.pojo.search.OrderItemSearchParam;
import com.billow.order.service.OrderItemService;
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
public class OrderItemServiceImpl extends HighLevelServiceImpl<OrderItemDao, OrderItemPo,OrderItemSearchParam> implements OrderItemService {

}


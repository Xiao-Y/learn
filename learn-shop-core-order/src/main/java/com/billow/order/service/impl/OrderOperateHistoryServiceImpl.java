package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.OrderOperateHistoryDao;
import com.billow.order.pojo.po.OrderOperateHistoryPo;
import com.billow.order.pojo.search.OrderOperateHistorySearchParam;
import com.billow.order.service.OrderOperateHistoryService;
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
public class OrderOperateHistoryServiceImpl extends HighLevelServiceImpl<OrderOperateHistoryDao, OrderOperateHistoryPo,OrderOperateHistorySearchParam> implements OrderOperateHistoryService {

}


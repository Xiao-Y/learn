package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.OrderReturnReasonDao;
import com.billow.order.pojo.po.OrderReturnReasonPo;
import com.billow.order.pojo.search.OrderReturnReasonSearchParam;
import com.billow.order.service.OrderReturnReasonService;
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
public class OrderReturnReasonServiceImpl extends HighLevelServiceImpl<OrderReturnReasonDao, OrderReturnReasonPo,OrderReturnReasonSearchParam> implements OrderReturnReasonService {

}


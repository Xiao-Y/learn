package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.OrderReturnApplyDao;
import com.billow.order.pojo.po.OrderReturnApplyPo;
import com.billow.order.pojo.search.OrderReturnApplySearchParam;
import com.billow.order.service.OrderReturnApplyService;
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
public class OrderReturnApplyServiceImpl extends HighLevelServiceImpl<OrderReturnApplyDao, OrderReturnApplyPo,OrderReturnApplySearchParam> implements OrderReturnApplyService {

}


package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.SeckillDao;
import com.billow.order.pojo.po.SeckillPo;
import com.billow.order.pojo.search.SeckillSearchParam;
import com.billow.order.service.SeckillService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 秒杀库存表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-21
 */
@Service
public class SeckillServiceImpl extends HighLevelServiceImpl<SeckillDao, SeckillPo, SeckillSearchParam> implements SeckillService {

}


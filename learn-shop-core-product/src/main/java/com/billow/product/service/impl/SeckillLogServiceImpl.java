package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.SeckillLogDao;
import com.billow.product.pojo.po.SeckillLogPo;
import com.billow.product.pojo.search.SeckillLogSearchParam;
import com.billow.product.service.SeckillLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@Service
public class SeckillLogServiceImpl extends HighLevelServiceImpl<SeckillLogDao, SeckillLogPo,SeckillLogSearchParam> implements SeckillLogService {

}


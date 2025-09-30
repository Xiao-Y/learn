
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.SeckillLogPo;
import com.billow.product.pojo.search.SeckillLogSearchParam;

/**
 * <p>
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。 服务类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
public interface SeckillLogService extends HighLevelService<SeckillLogPo,SeckillLogSearchParam> {

}
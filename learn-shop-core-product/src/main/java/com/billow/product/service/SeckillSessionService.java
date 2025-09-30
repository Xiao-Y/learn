
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.SeckillSessionPo;
import com.billow.product.pojo.search.SeckillSessionSearchParam;

/**
 * <p>
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。 服务类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
public interface SeckillSessionService extends HighLevelService<SeckillSessionPo,SeckillSessionSearchParam> {

    void loadData();
}
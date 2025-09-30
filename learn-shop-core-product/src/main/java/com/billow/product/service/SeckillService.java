
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.SeckillPo;
import com.billow.product.pojo.search.SeckillSearchParam;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。 服务类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
public interface SeckillService extends HighLevelService<SeckillPo, SeckillSearchParam> {


    /**
     * 自动任务加载数据到缓存中
     *
     * @author xiaoy
     * @since 2021/1/24 8:53
     */
    void loadSeckillJob();
}
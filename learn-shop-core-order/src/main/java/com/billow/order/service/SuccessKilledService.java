
package com.billow.order.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.order.pojo.po.SuccessKilledPo;
import com.billow.order.pojo.search.SuccessKilledSearchParam;

/**
 * <p>
 * 秒杀成功明细表 服务类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-21
 */
public interface SuccessKilledService extends HighLevelService<SuccessKilledPo, SuccessKilledSearchParam> {

    /**
     * 异步保存秒杀信息
     *
     * @param seckillId
     * @param userCode
     * @author liuyongtao
     * @since 2021-8-21 11:41
     */
    void saveAsync(Long seckillId, String userCode);
}
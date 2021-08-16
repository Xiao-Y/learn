
package com.billow.seckill.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.seckill.common.pojo.po.SuccessKilledPo;
import com.billow.seckill.common.pojo.search.SuccessKilledSearchParam;

/**
 * <p>
 * 秒杀成功明细表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
public interface SuccessKilledService extends HighLevelService<SuccessKilledPo, SuccessKilledSearchParam> {

    /**
     * 异步保存秒杀信息
     *
     * @param entity
     * @author liuyongtao
     * @since 2021-8-13 10:30
     */
    void saveAsync(SuccessKilledPo entity);
}

package com.billow.promotion.service;

import com.billow.promotion.pojo.vo.ExposerVo;
import com.billow.promotion.pojo.vo.SeckillExecutionVo;

/**
 * <p>
 * 秒杀库存表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
public interface SeckillService {

    /**
     * 生成秒杀链接
     *
     * @param seckillProductId 秒杀id
     * @return {@link ExposerVo}
     * @author liuyongtao
     * @since 2021-1-22 9:27
     */
    ExposerVo genSeckillUrl(Long seckillProductId);

    /**
     * 执行秒杀
     *
     * @param md5              校验码
     * @param seckillProductId 秒杀id
     * @param userCode         秒杀用户
     * @param expire           到期时间
     * @return {@link SeckillExecutionVo}
     * @author liuyongtao
     * @since 2021-8-17 11:58
     */
    SeckillExecutionVo executionSeckill(String md5, Long seckillProductId, String userCode, Long expire);
}

package com.billow.seckill.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.seckill.common.pojo.po.SeckillPo;
import com.billow.seckill.common.pojo.search.SeckillSearchParam;
import com.billow.seckill.common.pojo.vo.ExposerVo;
import com.billow.seckill.common.pojo.vo.SeckillExecutionVo;

/**
 * <p>
 * 秒杀库存表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
public interface SeckillService extends HighLevelService<SeckillPo, SeckillSearchParam> {

    /**
     * 生成秒杀链接
     *
     * @param seckillId 秒杀id
     * @return {@link ExposerVo}
     * @author liuyongtao
     * @since 2021-1-22 9:27
     */
    ExposerVo genSeckillUrl(Long seckillId);

    /**
     * 执行秒杀
     *
     * @param seckillId 秒杀id
     * @param md5       校验码
     * @return {@link SeckillExecutionVo}
     * @author liuyongtao
     * @since 2021-1-22 9:45
     */
    SeckillExecutionVo executionSeckill(Long seckillId, String md5, String userCode);

    /**
     * 自动任务加载数据到缓存中
     *
     * @author xiaoy
     * @since 2021/1/24 8:53
     */
    void loadSeckillJob();

}
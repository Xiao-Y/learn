
package com.billow.seckill.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.seckill.pojo.po.SeckillPo;
import com.billow.seckill.pojo.search.SeckillSearchParam;
import com.billow.seckill.pojo.vo.ExposerVo;
import com.billow.seckill.pojo.vo.SeckillExecutionVo;

import java.util.Date;

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
     * @param seckillProductId 秒杀id
     * @return {@link ExposerVo}
     * @author liuyongtao
     * @since 2021-1-22 9:27
     */
    ExposerVo genSeckillUrl(Long seckillProductId);

    /**
     * 执行秒杀
     *
     * @param md5       校验码
     * @param seckillProductId 秒杀id
     * @param userCode  秒杀用户
     * @param expire    到期时间
     * @return {@link SeckillExecutionVo}
     * @author liuyongtao
     * @since 2021-8-17 11:58
     */
    SeckillExecutionVo executionSeckill(String md5, Long seckillProductId, String userCode, Long expire);

    /**
     * 自动任务加载数据到缓存中
     *
     * @author xiaoy
     * @since 2021/1/24 8:53
     */
    void loadSeckillJob();

    /**
     * 更新秒杀商品信息，更新缓存
     *
     * @param nowDate   当前时间
     * @param seckillPo 更新对象，（完整对象）
     * @author xiaoy
     * @since 2021/1/24 10:39
     */
    void updatePojoAndCache(Date nowDate, SeckillPo seckillPo);
}
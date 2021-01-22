
package com.billow.seckill.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.seckill.pojo.po.SeckillPo;
import com.baomidou.mybatisplus.extension.service.IService;
import com.billow.seckill.pojo.vo.ExposerVo;
import com.billow.seckill.pojo.vo.SeckillExecutionVo;
import com.billow.seckill.pojo.vo.SeckillVo;

/**
 * <p>
 * 秒杀库存表 服务类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-01-21
 */
public interface SeckillService extends IService<SeckillPo> {

    /**
     * 分页查询
     *
     * @param seckillVo 查询条件
     * @return com.baomidou.mybatisplus.core.metadata.IPage<com.billow.seckill.pojo.po.SeckillPo>
     * @author billow
     * @since 2021-01-21
     */
    IPage<SeckillPo> findListByPage(SeckillVo seckillVo);

    /**
     * 根据ID禁用数据
     *
     * @param id 主键id
     * @return boolean
     * @author billow
     * @since 2021-01-21
     */
    boolean prohibitById(String id);

    /**
     * 生成秒杀链接
     *
     * @param seckillId 秒杀id
     * @return {@link ExposerVo}
     * @author liuyongtao
     * @since 2021-1-22 9:27
     */
    ExposerVo genSeckillUrl(String seckillId);

    /**
     * 执行秒杀
     *
     * @param seckillId 秒杀id
     * @param md5       校验码
     * @return {@link SeckillExecutionVo}
     * @author liuyongtao
     * @since 2021-1-22 9:45
     */
    SeckillExecutionVo executionSeckill(String seckillId, String md5, String userCode);
}
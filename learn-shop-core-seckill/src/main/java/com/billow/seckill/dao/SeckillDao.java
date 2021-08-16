package com.billow.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.seckill.common.pojo.po.SeckillPo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 秒杀库存表 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-01-21
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SeckillDao extends BaseMapper<SeckillPo> {

    /**
     * 减少库存
     *
     * @param seckillId
     * @return {@link int}
     * @author liuyongtao
     * @since 2021-1-22 11:21
     */
    int subStockById(@Param("seckillId") String seckillId);
}

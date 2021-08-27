package com.billow.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.seckill.pojo.po.SeckillSessionPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-08-27
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SeckillSessionDao extends BaseMapper<SeckillSessionPo> {

}

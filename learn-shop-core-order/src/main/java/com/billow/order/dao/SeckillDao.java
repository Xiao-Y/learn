package com.billow.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.order.pojo.po.SeckillPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 秒杀库存表 Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-21
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SeckillDao extends BaseMapper<SeckillPo> {

}

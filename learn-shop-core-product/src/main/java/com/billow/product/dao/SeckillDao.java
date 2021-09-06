package com.billow.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.product.pojo.po.SeckillPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。 Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SeckillDao extends BaseMapper<SeckillPo> {

}

package com.billow.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.product.pojo.po.SeckillLogPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 限时购通知记录表。用于存储会员的限时购预约记录，当有的限时购场次还未开始时，会员可以进行预约操作，当场次开始时，系统会进行提醒。 Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SeckillLogDao extends BaseMapper<SeckillLogPo> {

}

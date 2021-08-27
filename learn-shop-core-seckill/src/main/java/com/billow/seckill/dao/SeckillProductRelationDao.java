package com.billow.seckill.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.seckill.pojo.po.SeckillProductRelationPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-08-27
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SeckillProductRelationDao extends BaseMapper<SeckillProductRelationPo> {

}

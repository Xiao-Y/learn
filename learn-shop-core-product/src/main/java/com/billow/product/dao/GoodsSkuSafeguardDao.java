package com.billow.product.dao;

import com.billow.product.pojo.po.GoodsSkuSafeguardPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 * sku增值保障 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSkuSafeguardDao extends BaseMapper<GoodsSkuSafeguardPo> {

}

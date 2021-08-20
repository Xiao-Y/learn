package com.billow.product.dao;

import com.billow.product.pojo.po.GoodsSkuPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 * sku表（stock keeping uint 库存量单位） Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSkuDao extends BaseMapper<GoodsSkuPo> {

}

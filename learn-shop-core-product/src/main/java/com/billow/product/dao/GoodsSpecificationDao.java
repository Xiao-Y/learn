package com.billow.product.dao;

import com.billow.product.pojo.po.GoodsSpecificationPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 * 商品规格参数模板，json格式。 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSpecificationDao extends BaseMapper<GoodsSpecificationPo> {

}

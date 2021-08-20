package com.billow.product.dao;

import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 * 规格值表 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSpecValueDao extends BaseMapper<GoodsSpecValuePo> {

}

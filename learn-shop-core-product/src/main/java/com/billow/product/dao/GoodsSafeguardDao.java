package com.billow.product.dao;

import com.billow.product.pojo.po.GoodsSafeguardPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 * 增值保障 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSafeguardDao extends BaseMapper<GoodsSafeguardPo> {

}

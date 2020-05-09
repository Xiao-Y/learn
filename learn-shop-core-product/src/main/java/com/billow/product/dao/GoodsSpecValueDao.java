package com.billow.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 规格值表 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2020-05-09
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSpecValueDao extends BaseMapper<GoodsSpecValuePo> {

}

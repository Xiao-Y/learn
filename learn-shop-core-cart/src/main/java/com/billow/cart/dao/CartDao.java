package com.billow.cart.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.cart.pojo.po.CartPo;
import com.billow.mybatis.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * 购物车数据访问接口
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface CartDao extends BaseMapper<CartPo> {

} 
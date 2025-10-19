package com.billow.system.dao;

import com.billow.system.pojo.po.CityPo;
import com.billow.mybatis.base.HighLevelMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface CityDao extends HighLevelMapper<CityPo> {

}

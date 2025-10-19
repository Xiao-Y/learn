package com.billow.system.dao;

import com.billow.mybatis.base.HighLevelMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.system.pojo.po.RolePo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface RoleDao extends HighLevelMapper<RolePo> {

}

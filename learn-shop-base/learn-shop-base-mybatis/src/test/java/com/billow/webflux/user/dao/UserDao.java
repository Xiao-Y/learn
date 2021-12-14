package com.billow.webflux.user.dao;

import com.billow.webflux.user.pojo.po.UserPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-12-14
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface UserDao extends BaseMapper<UserPo> {

}

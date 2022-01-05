package com.billow.system.dao;

import com.billow.system.pojo.po.MenuPermissionPo;
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
 * @since 2022-01-04
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface MenuPermissionDao extends BaseMapper<MenuPermissionPo> {

}

package com.billow.system.dao;

import com.billow.system.pojo.po.MenuPermissionPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 * 菜单权限 Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-12-26
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface MenuPermissionDao extends BaseMapper<MenuPermissionPo> {

}

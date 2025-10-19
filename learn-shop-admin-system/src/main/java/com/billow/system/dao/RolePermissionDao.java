package com.billow.system.dao;

import com.billow.system.pojo.po.RolePermissionPo;
import com.billow.mybatis.base.HighLevelMapper;

import com.mybatisflex.core.query.QueryWrapper;
import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface RolePermissionDao extends HighLevelMapper<RolePermissionPo> {

    default List<RolePermissionPo> queryList(RolePermissionPo rolePermissionPo) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq(RolePermissionPo::getRoleId, rolePermissionPo.getId())
                .eq(RolePermissionPo::getValidInd, true);
        return this.selectListByQuery(wrapper);
    }
}


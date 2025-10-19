package com.billow.system.dao;

import com.billow.system.pojo.po.MenuPermissionPo;
import com.billow.mybatis.base.HighLevelMapper;

import com.billow.system.pojo.search.MenuPermissionSearchParam;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.core.update.UpdateWrapper;
import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

import java.util.Objects;

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
public interface MenuPermissionDao extends HighLevelMapper<MenuPermissionPo> {

    default void removeByCondition(MenuPermissionSearchParam searchParam) {
        QueryWrapper qw = QueryWrapper.create()
                .eq(MenuPermissionPo::getPermissionId, searchParam.getPermissionId(), Objects.nonNull(searchParam.getPermissionId()));
        this.deleteByQuery(qw);
    }
}

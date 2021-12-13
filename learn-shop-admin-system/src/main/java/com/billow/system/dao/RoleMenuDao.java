package com.billow.system.dao;

import com.billow.system.pojo.po.RoleMenuPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

import java.util.List;
import java.util.Set;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface RoleMenuDao extends BaseMapper<RoleMenuPo> {

    /**
     * 通过 rolecode 查询出角色的菜单ID
     *
     * @param roleCodes
     * @return {@link List< Long>}
     * @author xiaoy
     * @since 2021/12/13 21:59
     */
    Set<Long> findRoleMenuByRoleCode(Set<String> roleCodes);
}

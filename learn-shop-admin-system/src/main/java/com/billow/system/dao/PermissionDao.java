package com.billow.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.system.pojo.po.PermissionPo;
import org.apache.ibatis.annotations.CacheNamespace;

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
public interface PermissionDao extends BaseMapper<PermissionPo> {

    /**
     * 通过 rolecode 查询出，权限信息
     *
     * @param roleCodeList
     * @return {@link List< PermissionPo>}
     * @author xiaoy
     * @since 2021/12/12 18:02
     */
    List<PermissionPo> findPermissionByRoleCode(List<String> roleCodeList);

}

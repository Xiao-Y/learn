package com.billow.system.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.system.pojo.po.MenuPo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

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
public interface MenuDao extends BaseMapper<MenuPo>
{

    /**
     * 查询出指定角色下的菜单信息
     *
     * @param roleCodes 可以为空
     * @param isDisplay 是否只查询显示的菜单
     * @return List<MenuPo>
     * @author 千面
     * @date 2022/1/4 9:49
     */
    List<MenuPo> findMenuByRoleCode(@Param("roleCodes") List<String> roleCodes,
                                    @Param("isDisplay") boolean isDisplay);

    /**
     * 根据权限ID查询出绑定的菜单信息
     *
     * @param permissionId 权限ID
     * @return java.util.Set<com.billow.system.pojo.MenuPo>
     * @author LiuYongTao
     * @date 2019/7/22 17:55
     */
    List<MenuPo> findMenuByPermissionId(Long permissionId);
}

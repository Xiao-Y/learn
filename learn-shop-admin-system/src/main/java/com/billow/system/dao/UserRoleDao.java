package com.billow.system.dao;

import com.billow.mybatis.base.HighLevelMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.system.pojo.po.UserRolePo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

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
public interface UserRoleDao extends HighLevelMapper<UserRolePo> {

    /**
     * 通过用户id 查询出有效角色id
     *
     * @param userId 用户id
     * @return java.util.List<UserRolePo>
     * @author LiuYongTao
     * @date 2018/11/5 16:23
     */
    @Select("select * from sys_user_role r where r.user_id = #{userId}  and valid_ind = 1 ")
    List<UserRolePo> findByUserIdIsAndValidIndIsTrue(@Param("userId") Long userId);

    /**
     * 删除用户角色关联
     *
     * @param userId 用户id
     * @return void
     * @author LiuYongTao
     * @date 2019/7/31 10:28
     */
    @Delete("delete sys_user_role r where r.user_id = #{userId}")
    void deleteByUserId(@Param("userId") Long userId);
}

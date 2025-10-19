package com.billow.system.dao;


import com.billow.mybatis.base.HighLevelMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.system.pojo.po.UserPo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@CacheNamespace(implementation = MybatisRedisCache.class)
public interface UserDao extends HighLevelMapper<UserPo> {

    /**
     * 通过用户code 查询用户信息
     *
     * @param userCode
     * @return com.billow.user.pojo.po.UserPo
     * @author LiuYongTao
     * @date 2019/8/2 14:21
     */
    @Select("select * from u_user where usercode = #{userCode}")
    UserPo findByUsercode(@Param("userCode") String userCode);

    /**
     * 查询 userCode 的个数
     *
     * @param userCode
     * @return java.lang.Integer
     * @author billow
     * @date 2019/8/3 15:49
     */
    @Select("select count(1) from u_user where usercode = #{userCode} and valid_ind = 1")
    Integer countByUsercodeIsAndValidIndIsTrue(String userCode);
}
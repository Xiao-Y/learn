package com.billow.seckill.dao;

import com.billow.seckill.common.pojo.po.SuccessKilledPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 秒杀成功明细表 Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-01-21
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SuccessKilledDao extends BaseMapper<SuccessKilledPo> {

    /**
     * 忽略异常的保存
     *
     * @param killedPo
     * @author liuyongtao
     * @since 2021-1-22 10:23
     */
    int saveSuccessKilled(@Param("killedPo") SuccessKilledPo killedPo);
}

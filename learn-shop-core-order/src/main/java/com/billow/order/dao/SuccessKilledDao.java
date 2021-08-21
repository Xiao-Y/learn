package com.billow.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.order.pojo.po.SuccessKilledPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 秒杀成功明细表 Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-21
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface SuccessKilledDao extends BaseMapper<SuccessKilledPo> {

}

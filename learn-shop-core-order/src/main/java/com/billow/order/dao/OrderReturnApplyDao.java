package com.billow.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.order.pojo.po.OrderReturnApplyPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface OrderReturnApplyDao extends BaseMapper<OrderReturnApplyPo> {

}

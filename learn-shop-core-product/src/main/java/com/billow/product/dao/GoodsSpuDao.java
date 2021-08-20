package com.billow.product.dao;

import com.billow.product.pojo.po.GoodsSpuPo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import org.apache.ibatis.annotations.CacheNamespace;
import com.billow.mybatis.cache.MybatisRedisCache;

/**
 * <p>
 * spu表（Standard Product Unit, 标准产品单元） Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSpuDao extends BaseMapper<GoodsSpuPo> {

}

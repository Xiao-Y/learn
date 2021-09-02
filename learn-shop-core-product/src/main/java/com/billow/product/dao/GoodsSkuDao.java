package com.billow.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.product.pojo.po.GoodsSkuPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * sku表（stock keeping uint 库存量单位） Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSkuDao extends BaseMapper<GoodsSkuPo> {

}

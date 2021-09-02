package com.billow.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.product.pojo.po.GoodsSpuPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * spu表（Standard Product Unit, 标准产品单元） Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsSpuDao extends BaseMapper<GoodsSpuPo> {

}

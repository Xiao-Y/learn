package com.billow.product.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.product.pojo.po.GoodsCommentReplayPo;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * 产品评价回复表 Mapper 接口
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface GoodsCommentReplayDao extends BaseMapper<GoodsCommentReplayPo> {

}

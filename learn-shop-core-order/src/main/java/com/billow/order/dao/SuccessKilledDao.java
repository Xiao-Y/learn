package com.billow.order.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.order.pojo.po.SuccessKilledPo;
import org.apache.ibatis.annotations.CacheNamespace;
import org.apache.ibatis.annotations.Param;

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

    /**
     * 唯一键重复时，更新
     *
     * @param successKilledPo
     * @author liuyongtao
     * @since 2021-8-21 15:53
     */
    void saveOrUpdate(SuccessKilledPo successKilledPo);

    /**
     * 忽略异常的保存
     *
     * @param killedPo
     * @author liuyongtao
     * @since 2021-1-22 10:23
     */
    int saveIgnore(SuccessKilledPo killedPo);
}

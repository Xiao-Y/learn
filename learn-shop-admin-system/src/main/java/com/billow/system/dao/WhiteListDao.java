package com.billow.system.dao;

import com.billow.mybatis.base.HighLevelMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.system.pojo.po.WhiteListPo;
import com.mybatisflex.core.query.QueryWrapper;
import org.apache.ibatis.annotations.CacheNamespace;

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
public interface WhiteListDao extends HighLevelMapper<WhiteListPo> {

    default List<WhiteListPo> queryList(WhiteListPo whiteListPo) {
        QueryWrapper wrapper = QueryWrapper.create()
                .eq(WhiteListPo::getIp, whiteListPo.getIp())
                .eq(WhiteListPo::getModule, whiteListPo.getModule())
                .eq(WhiteListPo::getValidInd, whiteListPo.getValidInd());
        return this.selectListByQuery(wrapper);
    }
}

package com.billow.system.dao;

import com.billow.mybatis.base.HighLevelMapper;
import com.billow.mybatis.cache.MybatisRedisCache;
import com.billow.mybatis.utils.MybatisKet;
import com.billow.system.pojo.po.MytasklistPo;
import com.billow.system.pojo.search.MytasklistSearchParam;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.apache.ibatis.annotations.CacheNamespace;

/**
 * <p>
 * VIEW Mapper 接口
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@CacheNamespace(implementation = MybatisRedisCache.class)
public interface MytasklistDao extends HighLevelMapper<MytasklistPo> {

    default Page<MytasklistPo> selectPage(MytasklistSearchParam mytasklistSearchParam) {
        Page<MytasklistPo> page = new Page<>(mytasklistSearchParam.getPageNo(), mytasklistSearchParam.getPageSize());
        QueryWrapper queryWrapper = MybatisKet.getCondition(mytasklistSearchParam);
        // 排序
        MybatisKet.addSortBy(mytasklistSearchParam, queryWrapper);
        return this.paginate(page, queryWrapper);
    }
}

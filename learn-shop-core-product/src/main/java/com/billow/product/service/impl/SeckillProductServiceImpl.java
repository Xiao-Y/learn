package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.cache.SeckillProductCache;
import com.billow.product.dao.SeckillProductDao;
import com.billow.product.pojo.cache.SeckillProductCacheDto;
import com.billow.product.pojo.po.SeckillProductPo;
import com.billow.product.pojo.search.SeckillProductSearchParam;
import com.billow.product.service.SeckillProductService;
import com.billow.tools.utlis.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@Service
public class SeckillProductServiceImpl extends HighLevelServiceImpl<SeckillProductDao, SeckillProductPo, SeckillProductSearchParam> implements SeckillProductService {

    @Autowired
    private SeckillProductCache seckillProductCache;

    @Override
    public void loadData() {
        List<SeckillProductPo> seckillProductPos = this.lambdaQuery()
                .gt(SeckillProductPo::getSeckillCount, 0)
                .list();
        if (CollectionUtils.isNotEmpty(seckillProductPos)) {
            List<SeckillProductCacheDto> convert = ConvertUtils.convert(seckillProductPos, SeckillProductCacheDto.class);
            Map<String, SeckillProductCacheDto> collect = convert.stream()
                    .collect(Collectors.toMap(m -> m.getId().toString(), Function.identity()));
            seckillProductCache.saveSeckillProductCache(collect);
        }
    }
}


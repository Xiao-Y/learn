package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.cache.SeckillSessionCache;
import com.billow.product.common.enums.SeckillSessionStatusEnum;
import com.billow.product.dao.SeckillSessionDao;
import com.billow.product.pojo.cache.SeckillSessionCacheDto;
import com.billow.product.pojo.po.SeckillSessionPo;
import com.billow.product.pojo.search.SeckillSessionSearchParam;
import com.billow.product.service.SeckillSessionService;
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
 * 限时购场次表。用于存储限时购场次的信息，在一天中，一个限时购活动会有多个不同的活动时间段。 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@Service
public class SeckillSessionServiceImpl extends HighLevelServiceImpl<SeckillSessionDao, SeckillSessionPo,SeckillSessionSearchParam> implements SeckillSessionService {

    @Autowired
    private SeckillSessionCache seckillSessionCache;

    @Override
    public void loadData() {
        List<SeckillSessionPo> seckillSessionPos = this.lambdaQuery()
                .gt(SeckillSessionPo::getStatus, SeckillSessionStatusEnum.ON.getStatus())
                .list();
        if (CollectionUtils.isNotEmpty(seckillSessionPos)) {
            List<SeckillSessionCacheDto> convert = ConvertUtils.convert(seckillSessionPos, SeckillSessionCacheDto.class);
            Map<String, SeckillSessionCacheDto> collect = convert.stream()
                    .collect(Collectors.toMap(m -> m.getId().toString(), Function.identity()));
            seckillSessionCache.saveSeckillSessionCache(collect);
        }
    }
}


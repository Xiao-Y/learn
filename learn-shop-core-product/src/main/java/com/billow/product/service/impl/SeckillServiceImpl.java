package com.billow.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.cache.SeckillCache;
import com.billow.product.common.enums.SeckillStatusEnum;
import com.billow.product.dao.SeckillDao;
import com.billow.product.pojo.cache.SeckillCacheDto;
import com.billow.product.pojo.po.SeckillPo;
import com.billow.product.pojo.search.SeckillSearchParam;
import com.billow.product.service.SeckillProductService;
import com.billow.product.service.SeckillService;
import com.billow.product.service.SeckillSessionService;
import com.billow.tools.utlis.ConvertUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * <p>
 * 限时购表。用于存储限时购活动的信息，包括开始时间、结束时间以及上下线状态。 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
@Service
public class SeckillServiceImpl extends HighLevelServiceImpl<SeckillDao, SeckillPo, SeckillSearchParam> implements SeckillService {

    @Autowired
    private SeckillProductService seckillProductService;
    @Autowired
    private SeckillCache seckillCache;
    @Autowired
    private SeckillSessionService seckillSessionService;

    @Override
    public void loadSeckillJob() {
        // 加载 seckill
        this.loadData();
        // 加载 seckill_product
        seckillProductService.loadData();
        // 加载 seckill_session
        seckillSessionService.loadData();
    }

    private void loadData() {
        List<SeckillPo> seckillPos = this.lambdaQuery()
                .eq(SeckillPo::getStatus, SeckillStatusEnum.Yes.getStatus())
                .ge(SeckillPo::getEndDate, LocalDateTime.now())
                .list();
        if (CollectionUtils.isNotEmpty(seckillPos)) {
            List<SeckillCacheDto> convert = ConvertUtils.convert(seckillPos, SeckillCacheDto.class);
            Map<String, String> collect = convert.stream()
                    .collect(Collectors.toMap(m -> m.getId().toString(), JSON::toJSONString));
            seckillCache.saveSeckillCache(collect);
        }
    }
}


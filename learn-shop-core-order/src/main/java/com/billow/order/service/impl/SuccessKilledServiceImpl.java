package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.common.cache.SeckillCache;
import com.billow.order.dao.SuccessKilledDao;
import com.billow.order.pojo.po.SuccessKilledPo;
import com.billow.order.pojo.search.SuccessKilledSearchParam;
import com.billow.order.service.SuccessKilledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * <p>
 * 秒杀成功明细表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-21
 */
@Slf4j
@Service
public class SuccessKilledServiceImpl extends HighLevelServiceImpl<SuccessKilledDao, SuccessKilledPo, SuccessKilledSearchParam> implements SuccessKilledService {

    @Autowired
    private SeckillCache seckillCache;

    @Async
    @Transactional
    @Override
    public void saveAsync(Long seckillId, String userCode) {
        // 保存秒杀订单数据
        SuccessKilledPo successKilledPo = seckillCache.findSuccessKilledCache(seckillId, userCode);
        if (Objects.isNull(successKilledPo)) {
            log.info("没有查询到秒杀订单：seckillId：{},userCode:{}", seckillId, userCode);
            return;
        }
        super.save(successKilledPo);
    }
}


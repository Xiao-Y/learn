package com.billow.seckill.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.billow.common.amqp.config.MqSecKillOrderConfig;
import com.billow.common.amqp.expand.SendMessage;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.seckill.common.cache.SeckillCache;
import com.billow.seckill.dao.SuccessKilledDao;
import com.billow.seckill.pojo.po.SuccessKilledPo;
import com.billow.seckill.pojo.search.SuccessKilledSearchParam;
import com.billow.seckill.pojo.vo.SuccessKilledVo;
import com.billow.seckill.service.SuccessKilledService;
import com.billow.tools.utlis.ConvertUtils;
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
 * @version v1.0
 * @since 2021-01-21
 */
@Slf4j
@Service
public class SuccessKilledServiceImpl extends HighLevelServiceImpl<SuccessKilledDao, SuccessKilledPo, SuccessKilledSearchParam> implements SuccessKilledService {

    @Autowired
    private SeckillCache seckillCache;
    @Autowired
    private SuccessKilledDao successKilledDao;
    @Autowired
    private MqSecKillOrderConfig mqSecKillOrderConfig;

    @Override
    public void genQueryCondition(LambdaQueryWrapper<SuccessKilledPo> wrapper, SuccessKilledSearchParam successKilledVo) {
        wrapper.eq(Objects.nonNull(successKilledVo.getValidInd()), SuccessKilledPo::getValidInd, successKilledVo.getValidInd());
    }

    @Async
    @Transactional
    @Override
    public void saveAsync(SuccessKilledVo successKilledVo) {
        // 保存秒杀订单数据
        SuccessKilledPo successKilledPo = ConvertUtils.convert(successKilledVo, SuccessKilledPo.class);
        if (Objects.isNull(successKilledPo)) {
            assert successKilledVo != null;
            log.info("没有查询到秒杀订单：seckillId：{},userCode:{}", successKilledVo.getSeckillId(), successKilledVo.getUsercode());
            return;
        }
        int size = successKilledDao.saveIgnore(successKilledPo);
        if (size > 0) {
            SendMessage.send(mqSecKillOrderConfig.secKillOrderExchange().getName(), successKilledVo);
        }
    }
}


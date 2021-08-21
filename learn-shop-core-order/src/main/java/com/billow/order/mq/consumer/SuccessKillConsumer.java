package com.billow.order.mq.consumer;

import com.billow.order.common.cache.SeckillCache;
import com.billow.order.pojo.po.SuccessKilledPo;
import com.billow.order.pojo.vo.SuccessKilledVo;
import com.billow.order.service.SuccessKilledService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * 秒杀成功订单处理
 *
 * @author liuyongtao
 * @since 2021-8-20 11:02
 */
@Slf4j
@Component
public class SuccessKillConsumer {

    @Autowired
    private SeckillCache seckillCache;
    @Autowired
    private SuccessKilledService successKilledService;

    @RabbitHandler
    @RabbitListener(queues = "${config.mq.queue.secKillToCoreOrder}")
    public void secKillOrder(SuccessKilledVo data) {
        log.info("秒杀订单数据:{}", data);
        Long seckillId = data.getSeckillId();
        String userCode = data.getUsercode();
        // 保存秒杀订单数据
        SuccessKilledPo successKilledPo = seckillCache.findSuccessKilledCache(seckillId, userCode);
        if (Objects.isNull(successKilledPo)) {
            log.info("没有查询到秒杀订单：seckillId：{},userCode:{}", seckillId, userCode);
            return;
        }
        successKilledService.saveAsync(successKilledPo);
    }
}

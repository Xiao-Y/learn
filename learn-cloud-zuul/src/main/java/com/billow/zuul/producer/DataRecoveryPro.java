package com.billow.zuul.producer;

import com.billow.zuul.config.MqExecuteSqlConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 发送mq
 *
 * @author liuyongtao
 * @create 2019-08-11 10:28
 */
@Slf4j
@Component
public class DataRecoveryPro {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private MqExecuteSqlConfig mqExecuteSqlConfig;

    /**
     * 执行sql 和 加载缓存
     *
     * @return void
     * @author billow
     * @date 2019/8/11 10:33
     */
    public void dataRecovery() {
        String exchange = mqExecuteSqlConfig.getExchange();
        String routeKey = mqExecuteSqlConfig.getRouteKey();
        String queue = mqExecuteSqlConfig.getQueue();
        log.info("exchange:{},routeKey:{},queue{}，发送初始化 sql 的 mq", exchange, routeKey, queue);
        amqpTemplate.convertAndSend(exchange, routeKey, "开始执行sql...");
    }
}

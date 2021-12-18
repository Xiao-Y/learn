package com.billow.gateway.receive;

import com.billow.cloud.common.amqp.AmqpYml;
import com.billow.notice.amqp.service.SendMQService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 发送mq
 *
 * @author liuyongtao
 * @create 2019-08-11 10:28
 */
@Slf4j
@Component
public class DataRecoveryReceive
{
    @Resource
    private AmqpYml executeSql;
    @Autowired
    private SendMQService sendMQService;

    /**
     * 执行sql 和 加载缓存
     *
     * @return void
     * @author billow
     * @date 2019/8/11 10:33
     */
    public void dataRecovery()
    {
        String exchange = executeSql.getExchange();
        String routeKey = executeSql.getRouteKey();
        log.info("exchange:{},routeKey:{}，发送初始化 sql 的 mq", exchange, routeKey);
        sendMQService.send(exchange, routeKey, "开始执行sql...");
    }
}

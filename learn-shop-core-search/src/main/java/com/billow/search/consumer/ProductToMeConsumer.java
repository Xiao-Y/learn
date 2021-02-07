package com.billow.search.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 商品系统 发送过来的消息
 *
 * @author liuyongtao
 * @create 2019-08-11 11:14
 */
@Slf4j
@Component
public class ProductToMeConsumer {

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Async("fxbDrawExecutor")
    @RabbitListener(queues = "${config.mq.queue.refreshEs}")
    @RabbitHandler
    public void refreshEs(String message) throws Exception {
        log.info(message);
        log.info("开始初始化 SQL...");
        try {
//            elasticsearchRestTemplate.exists()
        } catch (Exception e) {
            log.error("刷新 es 缓存异常");
        }
        log.info("完成初始化 SQL...");

        log.info("开始初始化 Redis ...");
    }
}

package com.billow.search.consumer;

import com.alibaba.fastjson.JSON;
import com.billow.search.pojo.SpuInfo;
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
    private ElasticsearchRestTemplate restTemplate;

    @Async("fxbDrawExecutor")
    @RabbitListener(queues = "${config.mq.queue.refreshEs}")
    @RabbitHandler
    public void refreshEs(String message) throws Exception {
        log.info("获取的信息：{}", message);
        SpuInfo spuInfo = JSON.parseObject(message, SpuInfo.class);

        try {
            boolean exists = restTemplate.exists("1", SpuInfo.class);
            if (!exists) {
                restTemplate.save(spuInfo);
            }
        } catch (Exception e) {
            log.error("刷新 es 缓存异常", e);
        }
        log.info("完成刷新...");
    }

    /**
     * 同步 mysql 数据到 es
     *
     * @param message
     * @author liuyongtao
     * @since 2021-9-1 15:34
     */
    @Async("fxbDrawExecutor")
    @RabbitListener(queues = "canal_queue")
    @RabbitHandler
    public void syncEs(String message) throws Exception {
        log.info("mysql：{}", message);
        SpuInfo spuInfo = JSON.parseObject(message, SpuInfo.class);

        try {
            boolean exists = restTemplate.exists("1", SpuInfo.class);
            if (!exists) {
                restTemplate.save(spuInfo);
            }
        } catch (Exception e) {
            log.error("刷新 es 缓存异常", e);
        }
        log.info("完成刷新...");
    }
}

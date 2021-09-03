package com.billow.search.consumer;

import com.alibaba.fastjson.JSON;
import com.billow.search.pojo.po.GoodsInfoPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 商品系统 发送过来的消息（商品上下架）
 *
 * @author liuyongtao
 * @create 2019-08-11 11:14
 */
@Slf4j
@Component
public class RefreshEsConsumer {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

    @Async("fxbDrawExecutor")
    @RabbitListener(queues = "${config.mq.queue.refreshEs}")
    @RabbitHandler
    public void refreshEs(String message) {
        log.info("获取的信息：{}", message);
        GoodsInfoPo goodsInfoPo = JSON.parseObject(message, GoodsInfoPo.class);
        log.info("完成刷新...");
    }
}

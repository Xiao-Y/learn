package com.billow.search.consumer;

import com.alibaba.fastjson.JSON;
import com.billow.search.pojo.SpuInfo;
import com.billow.search.pojo.vo.CanalDbVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * 数据库同步数据到es
 *
 * @author liuyongtao
 * @since 2021-9-2 8:18
 */
@Slf4j
@Component
public class DbSyncEsConsumer {

    @Autowired
    private ElasticsearchRestTemplate restTemplate;

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
    public void syncEs(String message) {
        log.info("mysql：{}", message);
        CanalDbVo canalDbVo = JSON.parseObject(message, CanalDbVo.class);

        log.info("====>>>{}", canalDbVo);

//        try {
//            boolean exists = restTemplate.exists("1", SpuInfo.class);
//            if (!exists) {
//                restTemplate.save(spuInfo);
//            }
//        } catch (Exception e) {
//            log.error("刷新 es 缓存异常", e);
//        }
        log.info("完成刷新...");
    }
}

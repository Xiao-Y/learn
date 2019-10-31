package com.billow.system.consumer;

import com.billow.system.init.StartLoading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * Zuul 发送过来的消息
 *
 * @author liuyongtao
 * @create 2019-08-11 11:14
 */
@Slf4j
@Component
public class ZuulToMeConsumer {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private StartLoading startLoading;

    @Async("fxbDrawExecutor")
    @RabbitListener(queues = "${config.mq.queue.executeSql}")
    @RabbitHandler
    public void executesql(String message) throws Exception {
        log.info(message);
        log.info("开始初始化 SQL...");
        try {
            Resource resource = new ClassPathResource("sql/learn-shop.sql");
            ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("完成初始化 SQL...");

        log.info("开始初始化 Redis ...");
        startLoading.init(null);
    }
}

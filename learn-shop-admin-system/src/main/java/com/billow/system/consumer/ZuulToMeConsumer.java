package com.billow.system.consumer;

import com.billow.system.init.StartLoading;
import com.billow.tools.utlis.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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

    // 线程池长期维持的线程数
    private static final int corePoolSize = 8;
    // 线程数的上限
    private static final int maximumPoolSize = 10;
    // 超过corePoolSize的线程的idle时长，超过这个时间，多余的线程会被回收。
    private static final int keepAlivelime = 0;
    // 任务的排队队列
    private static final int capacity = 512;

    @RabbitListener(queues = "${config.mq.zuulToSystem.executesqlRoutingKey}")
    @RabbitHandler
    public void executesql(String message) throws Exception {
        log.info(message);

        ExecutorService pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAlivelime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity), // 使用有界队列，避免OOM
                new ThreadPoolExecutor.DiscardPolicy());
        pool.execute(() -> {
            log.info("开始初始化 SQL...");
            try {
                Resource resource = new ClassPathResource("learn-shop.sql");
                ScriptUtils.executeSqlScript(dataSource.getConnection(), resource);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            log.info("完成初始化 SQL...");

            log.info("开始初始化 Redis ...");
            startLoading.init(null);
        });
        pool.shutdown();
    }
}

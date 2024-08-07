package com.billow.common.thread;

import com.billow.tools.thread.ThreadMdcUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 线程池配置
 */
@Slf4j
@Configuration
@EnableAsync
public class ThreadExecutorConfig {

    // 线程池长期维持的线程数
    @Value("${custom.thread-pool.core-pool-size: 8}")
    private int corePoolSize;
    // 线程数的上限
    @Value("${custom.thread-pool.maximum-pool-size: 10}")
    private static final int maximumPoolSize = 10;
    // 任务的排队队列
    @Value("${custom.thread-pool.capacity: 512}")
    private static final int capacity = 512;

    @Bean
    public ExecutorService fxbDrawExecutor() {
        ThreadMdcUtil.ThreadPoolTaskExecutorMdcWrapper executor = new ThreadMdcUtil.ThreadPoolTaskExecutorMdcWrapper();
//        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        executor.setQueueCapacity(capacity);
        executor.setThreadNamePrefix("fxb-draw-service-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }
}
package com.billow.system.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 启动加载数据
 *
 * @author liuyongtao
 * @create 2019-07-22 16:03
 */
@Component
public class StartLoading implements InitializingBean {

    @Autowired
    private Map<String, IStartLoading> startLoading;

    // 线程池长期维持的线程数
    private static final int corePoolSize = 8;
    // 线程数的上限
    private static final int maximumPoolSize = 10;
    // 超过corePoolSize的线程的idle时长，超过这个时间，多余的线程会被回收。
    private static final int keepAlivelime = 0;
    // 任务的排队队列
    private static final int capacity = 512;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init(null);
    }

    public boolean init(String cacheType) {
        ExecutorService pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAlivelime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity), // 使用有界队列，避免OOM
                new ThreadPoolExecutor.DiscardPolicy());
        if (cacheType == null) {
            for (Map.Entry<String, IStartLoading> entry : startLoading.entrySet()) {
                pool.execute(() -> entry.getValue().init());
            }
        } else {
            pool.execute(() -> {
                IStartLoading loading = startLoading.get(cacheType);
                if (loading != null) {
                    loading.init();
                }
            });
        }
        pool.shutdown();
        return true;
    }
}

package com.billow.system.api;

import com.billow.system.init.IStartLoading;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 缓存管理
 *
 * @author liuyongtao
 * @create 2019-08-10 11:06
 */
@Slf4j
@RestController
@RequestMapping("/cacheApi")
@Api(value = "缓存管理")
public class CacheApi {

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

    @PutMapping("/initAll")
    @ApiOperation("初始化所有缓存，initDictionary,initRoleMenu,initRolePermission")
    public boolean initAll() {
        ExecutorService pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAlivelime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity), // 使用有界队列，避免OOM
                new ThreadPoolExecutor.DiscardPolicy());
        for (Map.Entry<String, IStartLoading> entry : startLoading.entrySet()) {
            pool.execute(() -> entry.getValue().init());
        }
        pool.shutdown();
        return true;
    }


    @PutMapping("/init/{cacheType}")
    @ApiOperation("初始化指定的缓存，initDictionary,initRoleMenu,initRolePermission")
    public boolean initCacheByType(@PathVariable("cacheType") String cacheType) {
        ExecutorService pool = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAlivelime, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(capacity), // 使用有界队列，避免OOM
                new ThreadPoolExecutor.DiscardPolicy());
        pool.execute(() -> {
            IStartLoading initDictionary = startLoading.get(cacheType);
            if (initDictionary != null) {
                initDictionary.init();
            }
        });
        pool.shutdown();
        return true;
    }

}

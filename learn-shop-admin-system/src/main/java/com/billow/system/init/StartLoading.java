package com.billow.system.init;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ExecutorService;

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

    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;

    @Override
    public void afterPropertiesSet() throws Exception {
        this.init(null);
    }

    public boolean init(String cacheType) {
        if (cacheType == null) {
            for (Map.Entry<String, IStartLoading> entry : startLoading.entrySet()) {
                executorService.execute(() -> entry.getValue().init());
            }
        } else {
            executorService.execute(() -> {
                IStartLoading loading = startLoading.get(cacheType);
                if (loading != null) {
                    loading.init();
                }
            });
        }
        return true;
    }
}

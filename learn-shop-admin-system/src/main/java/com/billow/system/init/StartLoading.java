package com.billow.system.init;

import com.billow.system.properties.CustomProperties;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

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
    @Autowired
    private CustomProperties customProperties;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (customProperties.getCommon().getStartInitData()) {
            this.init(null);
        }
    }

    public boolean init(String cacheType) {
        if (cacheType == null) {
            for (Map.Entry<String, IStartLoading> entry : startLoading.entrySet()) {
                entry.getValue().init();
            }
        } else {
            IStartLoading loading = startLoading.get(cacheType);
            if (loading != null) {
                loading.init();
            }
        }
        return true;
    }
}

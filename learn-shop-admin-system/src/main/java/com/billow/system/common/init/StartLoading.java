package com.billow.system.common.init;

import com.billow.system.common.properties.CustomProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 启动加载数据
 *
 * @author liuyongtao
 * @create 2019-07-22 16:03
 */
@Slf4j
@RefreshScope
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
        } else {
            log.warn("未开启缓存加载，首次启动时需要设置为true，对应配置：custom.common.start-init-data");
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

    @Value(value = "${custom.common.start-init-data:true}")
    public void setStartInitData(boolean startInitData) {
        log.warn("配置 custom.common.start-init-data 改变为：{}", startInitData);
        this.init(null);
    }
}

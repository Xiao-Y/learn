package com.billow.system.common.config;

import cn.hutool.core.util.BooleanUtil;
import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigChangeEvent;
import com.alibaba.nacos.api.config.ConfigChangeItem;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.client.config.listener.impl.AbstractConfigChangeListener;
import com.billow.system.common.init.StartLoading;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * nocas 配置文件的监听
 */
@Slf4j
@Component
public class NacosConfigListener {

    // 指定要监听的配置文件ID和分组
    private final static String DATA_ID = "learn-shop-admin-system.yml";
    private final static String GROUP = "DEFAULT_GROUP";

    @Autowired
    private StartLoading startLoading;

    @Resource//注入NacosConfigManager
    public void nacosListen(NacosConfigManager nacosConfigManager) {
        //获取配置中心服务
        ConfigService configService = nacosConfigManager.getConfigService();
        try {
            //对配置中心添加监听(配置文件的dataId,group)
            configService.addListener(DATA_ID, GROUP, new AbstractConfigChangeListener() {
                //监听后的处理逻辑
                @Override
                public void receiveConfigChange(ConfigChangeEvent configChangeEvent) {
                    for (ConfigChangeItem changeItem : configChangeEvent.getChangeItems()) {
                        // 监听 custom.common.start-init-data 的变化
                        if ("custom.common.start-init-data".equals(changeItem.getKey())) {
                            // 初始菜单和权限缓存数据
                            boolean startInitData = BooleanUtil.toBoolean(changeItem.getNewValue());
                            log.info("========>>>>>> 是否初始化缓存数据：{} ", startInitData);
                            if (!startInitData) {
                                continue;
                            }
                            startLoading.init(null);
                        }
                    }
                }
            });
        } catch (NacosException e) {
            log.error("监听配置文件失败", e);
            throw new RuntimeException(e);
        }
    }
}

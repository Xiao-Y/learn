package com.billow.common.shardingsphere.loader;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.exception.NacosException;
import com.billow.tools.utlis.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.shardingsphere.infra.url.spi.ShardingSphereURLLoader;

import java.util.Properties;

/**
 * 读取nacos 配置
 */
@Slf4j
public final class ShardingSphereSpringNacosURLLoader implements ShardingSphereURLLoader {

    private static final Long TIMEOUT = 3000L;

    @Override
    @SneakyThrows(RuntimeException.class)
    public String load(final String configurationSubject, final Properties queryProps) {
        NacosConfigManager nacosConfigManager = SpringContextUtil.getBean(NacosConfigManager.class);
        try {
            String db = nacosConfigManager.getConfigService()
                    .getConfig("sys-config-db.yml", "DEFAULT_GROUP", TIMEOUT);
            String shardingConfig = nacosConfigManager.getConfigService()
                    .getConfig("sys-config-sharding-jdbc.yml", "DEFAULT_GROUP", TIMEOUT);
            return (db + "\n" + shardingConfig).replaceAll("(?<=- )#", " "); // 替换掉额外加的#符号
        } catch (NacosException e) {
            log.error("Sharding-jdbc 配置加载异常:", e);
        }
        return "";
    }

    @Override
    public String getType() {
        return "nacos:";
    }

}

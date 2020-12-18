package com.billow.gateway.security.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 网关白名单配置
 */
@Data
public class WhiteListProperties {

    // 白名单列表
    private List<String> urls = new ArrayList<>();

}
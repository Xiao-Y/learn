package com.billow.gateway.security.properties;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 关于安全的配置
 *
 * @author liuyongtao
 * @create 2019-05-22 14:25
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Component
@ConfigurationProperties(prefix = "secure")
public class SecurityProperties {
    // 登陆客户端配置
    private ClientProperties client = new ClientProperties();
    // 网关白名单配置
    private List<String> whiteList = new ArrayList<>();
    // 配置需要权限的
    private List<String> needCheck = new ArrayList<>();

}

package com.billow.zuul.properties;

import lombok.Data;

/**
 * 关于安全的配置
 *
 * @author liuyongtao
 * @create 2019-05-22 14:25
 */
@Data
public class SecurityProperties {

    // 认证服务器的地址
    private String authServer;

}

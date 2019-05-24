package com.billow.system.properties;

/**
 * 关于安全的配置
 *
 * @author liuyongtao
 * @create 2019-05-22 14:25
 */
public class SecurityProperties {

    // 认证服务器的地址
    private String authServer;

    public String getAuthServer() {
        return authServer;
    }

    public SecurityProperties setAuthServer(String authServer) {
        this.authServer = authServer;
        return this;
    }
}

package com.billow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;

/**
 * 认证，受权中心
 *
 * @author billow
 * @date 2018-05-16 16:45
 * @ EnableEurekaClient 开启客户端发现 <br/>
 * @ EnableHystrix 开启断路器 <br/>
 * @ EnableHystrixDashboard 开启熔断监控仪表盘 <br/>
 * @ ServletComponentScan 用于加载servlet <br/>
 */
@EnableHystrix
@EnableHystrixDashboard
@EnableEurekaClient
@SpringBootApplication
@ServletComponentScan
@EnableAuthorizationServer
public class OAuthApp {

    public static void main(String[] args) {
        SpringApplication.run(OAuthApp.class);
    }
}

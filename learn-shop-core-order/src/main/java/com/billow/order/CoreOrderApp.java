package com.billow.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @ EnableEurekaClient 开启客户端发现
 * @ EnableFeignClients 开启Feign远程调用
 * @ EnableHystrix 开启断路器
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients //(basePackages={"com.billow.remote"})
@EnableHystrix
@EnableHystrixDashboard
@ServletComponentScan
@ComponentScan("com.billow")
public class CoreOrderApp {
    public static void main(String[] args) {
        SpringApplication.run(CoreOrderApp.class, args);
    }
}

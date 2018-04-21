package com.ft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;

/**
 * @ EnableEurekaClient 开启客户端发现
 * @ EnableFeignClients 开启Feign远程调用
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 * @ EnableCircuitBreaker 开启断路器
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages={"com.ft.remote"})
@EnableHystrixDashboard
@EnableCircuitBreaker
@EnableAutoConfiguration
@ServletComponentScan
public class CoreOrderApp {
    public static void main(String[] args) {
        SpringApplication.run(CoreOrderApp.class, args);
    }
}

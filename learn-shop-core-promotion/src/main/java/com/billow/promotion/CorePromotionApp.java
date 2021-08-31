package com.billow.promotion;

import com.billow.tools.utlis.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ EnableEurekaClient 开启客户端发现
 * @ EnableFeignClients 开启Feign远程调用
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 * @ EnableCircuitBreaker 开启断路器
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
public class CorePromotionApp {
    public static void main(String[] args) {
        SpringContextUtil.setApplicationContext(SpringApplication.run(CorePromotionApp.class, args));
    }
}

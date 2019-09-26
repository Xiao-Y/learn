package com.billow.test;

import com.billow.tools.utlis.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @ EnableEurekaClient 开启客户端发现
 * @ EnableFeignClients 开启Feign远程调用
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 * @ EnableCircuitBreaker 开启断路器
 */
//@EnableJpaAuditing
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients
@EnableHystrixDashboard
@EnableCircuitBreaker
@ComponentScan("com.billow")
// job 模块也有实例类所有需要扫描
@EntityScan(basePackages = {"com.billow.job", "com.billow.test"})
@EnableJpaRepositories(basePackages = {"com.billow.job", "com.billow.test"})
public class TestJobApp {
    public static void main(String[] args) {
        SpringContextUtil.setApplicationContext(SpringApplication.run(TestJobApp.class, args));
    }
}

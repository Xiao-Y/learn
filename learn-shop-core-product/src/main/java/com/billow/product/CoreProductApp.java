package com.billow.product;

import com.billow.tools.utlis.SpringContextUtil;
import jakarta.annotation.PostConstruct;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @ EnableEurekaClient 开启客户端发现
 * @ EnableFeignClients 开启Feign远程调用
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 * @ EnableCircuitBreaker 开启断路器
 */
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
@MapperScan("com.billow.*.dao")
public class CoreProductApp {
    public static void main(String[] args) {
        SpringContextUtil.setApplicationContext(SpringApplication.run(CoreProductApp.class, args));
    }


    @Autowired
    private ConfigurableEnvironment env;

    @PostConstruct
    public void checkConfig() {
        String property = env.getProperty("spring.redisson.thread");
        System.out.println("Redisson thread config: " + property); // 输出是否为 null
    }
}

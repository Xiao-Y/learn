package com.billow.seckill;

import com.billow.tools.utlis.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @ EnableEurekaClient 开启客户端发现
 * @ EnableFeignClients 开启Feign远程调用
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 * @ EnableCircuitBreaker 开启断路器
 */
@EnableFeignClients
@SpringBootApplication
@EnableDiscoveryClient
@MapperScan("com.billow.*.dao")
public class CoreSeckillApp {
    public static void main(String[] args) {
        SpringContextUtil.setApplicationContext(SpringApplication.run(CoreSeckillApp.class, args));
    }
}

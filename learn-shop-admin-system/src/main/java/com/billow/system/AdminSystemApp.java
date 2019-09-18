package com.billow.system;

import com.billow.tools.utlis.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * @ EnableEurekaClient 开启客户端发现
 * @ EnableFeignClients 开启Feign远程调用
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 * @ EnableHystrix 开启断路器
 * @ EnableJpaAuditing jpa 审计功能
 */
@EnableJpaAuditing
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.billow")
// 邮件模块也有实例类所有需要扫描
@EntityScan(basePackages = {"com.billow.email", "com.billow.system"})
@EnableJpaRepositories(basePackages = {"com.billow.email", "com.billow.system"})
public class AdminSystemApp {
    public static void main(String[] args) {
        SpringContextUtil.setApplicationContext(SpringApplication.run(AdminSystemApp.class, args));
    }
}

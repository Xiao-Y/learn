package com.billow.system;

import com.billow.tools.utlis.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @ EnableEurekaClient 开启客户端发现
 * @ EnableFeignClients 开启Feign远程调用
 * @ EnableHystrixDashboard 开启熔断监控仪表盘
 * @ EnableHystrix 开启断路器
 * @ EnableJpaAuditing jpa 审计功能
 */
@EnableFeignClients
@EnableDiscoveryClient
// 两个安全配置。引入的activiti-spring-boot-starter-basic 依赖中存在了一个自动安全配置类,
// 所以排除掉 activiti-spring-boot-starter-basic中的安全配置类 SecurityAutoConfiguration ,
// 在启动类配置 （注意不要导错包，正确的包为org.activiti.spring.boot.SecurityAutoConfiguration）
@SpringBootApplication
@MapperScan("com.billow.system.dao")
public class AdminSystemApp {
    public static void main(String[] args) {
        SpringContextUtil.setApplicationContext(SpringApplication.run(AdminSystemApp.class, args));
    }
}

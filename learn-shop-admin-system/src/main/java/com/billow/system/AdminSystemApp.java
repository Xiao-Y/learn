package com.billow.system;

import com.billow.tools.utlis.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
@SpringBootApplication(scanBasePackages = {"com.billow.aop", "com.billow.system"})
@MapperScan("com.billow.system.dao")
public class AdminSystemApp implements ApplicationContextAware {
    public static void main(String[] args) {
        SpringApplication.run(AdminSystemApp.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.setApplicationContext(applicationContext);
    }
}

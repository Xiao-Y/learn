package com.billow.user;

import com.billow.tools.utlis.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableHystrix
@EnableHystrixDashboard
@EnableFeignClients("com.billow")
@EnableEurekaClient
@SpringBootApplication
@ComponentScan("com.billow")
@ServletComponentScan
public class AdminUserApp {

    public static void main(String[] args) {
        //放入到ApplicationContext中可以在系统中使用getBean获取
        SpringContextUtil.setApplicationContext(SpringApplication.run(AdminUserApp.class, args));
    }
}

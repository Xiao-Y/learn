package com.billow.user;

import com.billow.tools.utlis.SpringContextUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableFeignClients("com.billow")
@EnableDiscoveryClient
@SpringBootApplication
@ServletComponentScan
public class AdminUserApp {

    public static void main(String[] args) {
        //放入到ApplicationContext中可以在系统中使用getBean获取
        SpringContextUtil.setApplicationContext(SpringApplication.run(AdminUserApp.class, args));
    }
}

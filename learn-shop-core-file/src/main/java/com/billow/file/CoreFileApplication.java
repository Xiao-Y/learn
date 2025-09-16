package com.billow.file;

import com.billow.tools.utlis.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.billow.aop", "com.billow.file"})
@MapperScan("com.billow.file.dao")
public class CoreFileApplication {

    public static void main(String[] args) {
        SpringContextUtil.setApplicationContext(SpringApplication.run(CoreFileApplication.class, args));
    }

}

package com.billow.search;

import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EsMapperScan("com.billow.search.dao")
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.billow.aop", "com.billow.search"})
public class CoreSearchApp {

    public static void main(String[] args) {
        SpringApplication.run(CoreSearchApp.class, args);
    }
}

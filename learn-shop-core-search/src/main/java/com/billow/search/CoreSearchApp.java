package com.billow.search;

import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EsMapperScan("com.billow.search.dao")
@EnableFeignClients
@SpringBootApplication
public class CoreSearchApp {

    public static void main(String[] args) {
        SpringApplication.run(CoreSearchApp.class, args);
    }
}

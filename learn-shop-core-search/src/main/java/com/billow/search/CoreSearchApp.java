package com.billow.search;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@SpringBootApplication
public class CoreSearchApp {

    public static void main(String[] args) {
        SpringApplication.run(CoreSearchApp.class, args);
    }
}

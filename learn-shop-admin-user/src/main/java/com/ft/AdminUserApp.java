package com.ft;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class AdminUserApp {
    public static void main(String[] args) {
        SpringApplication.run(AdminUserApp.class, args);
    }
}

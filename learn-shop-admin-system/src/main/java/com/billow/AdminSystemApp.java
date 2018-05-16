package com.billow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableAutoConfiguration
@EnableEurekaClient
@SpringBootApplication
@ServletComponentScan
public class AdminSystemApp {
    public static void main(String[] args){
        SpringApplication.run(AdminSystemApp.class, args);
    }
}

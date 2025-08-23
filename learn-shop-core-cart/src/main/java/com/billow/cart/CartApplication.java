package com.billow.cart;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 购物车服务启动类
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.billow.aop", "com.billow.cart"})
public class CartApplication {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }
} 
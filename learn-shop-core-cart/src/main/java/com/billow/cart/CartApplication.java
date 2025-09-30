package com.billow.cart;

import com.billow.tools.utlis.SpringContextUtil;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * 购物车服务启动类
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@EnableAsync
@SpringBootApplication(scanBasePackages = {"com.billow.aop", "com.billow.cart"})
@EnableDiscoveryClient
@EnableFeignClients
@MapperScan("com.billow.*.dao")
public class CartApplication implements ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(CartApplication.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.setApplicationContext(applicationContext);
    }
}
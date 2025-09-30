package com.billow.search;

import com.billow.tools.utlis.SpringContextUtil;
import org.dromara.easyes.starter.register.EsMapperScan;
import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@EsMapperScan("com.billow.search.dao")
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.billow.aop", "com.billow.search"})
public class CoreSearchApp implements ApplicationContextAware {

    public static void main(String[] args) {
        SpringApplication.run(CoreSearchApp.class, args);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.setApplicationContext(applicationContext);
    }
}

package com.billow.aop.config;

import com.billow.aop.advice.GlobalExceptionHandler;
import com.billow.aop.advice.GlobalResponseHandler;
import com.billow.aop.aspect.OperationLogsAspect;
import org.hibernate.validator.HibernateValidator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * valid 校验配置
 *
 * @author liuyongtao
 * @since 2021-1-27 14:15
 */
@Configuration
public class AopConfig {

    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)// 快速失败（只要有一个失败就返回）
                .buildValidatorFactory()
                .getValidator();
    }

    @Bean
    public OperationLogsAspect operationLogsAspect(){
        return  new OperationLogsAspect();
    }

    @Bean
    public GlobalExceptionHandler globalExceptionHandler(){
        return  new GlobalExceptionHandler();
    }

    @Bean
    public GlobalResponseHandler globalResponseHandler(){
        return  new GlobalResponseHandler();
    }
}
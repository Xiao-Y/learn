package com.billow.aop.config;

import com.billow.aop.advice.GlobalExceptionHandler;
import com.billow.aop.advice.GlobalResponseHandler;
import com.billow.aop.aspect.OperationLogsAspect;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.hibernate.validator.HibernateValidator;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.Validation;
import javax.validation.Validator;

/**
 * @author liuyongtao
 * @since 2021-1-27 14:15
 */
@Configuration
public class AopConfig {

    // valid 校验配置
    @Bean
    public Validator validator() {
        return Validation.byProvider(HibernateValidator.class)
                .configure()
                .failFast(true)// 快速失败（只要有一个失败就返回）
                .buildValidatorFactory()
                .getValidator();
    }

    // 日志
    @Bean
    public OperationLogsAspect operationLogsAspect() {
        return new OperationLogsAspect();
    }

    // 全局异常处理器
    @Bean
    public GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    // 全局统一返回处理器
    @Bean
    public GlobalResponseHandler globalResponseHandler() {
        return new GlobalResponseHandler();
    }

    // 全局统一 long 转 string
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return jacksonObjectMapperBuilder -> {
            jacksonObjectMapperBuilder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            jacksonObjectMapperBuilder.serializerByType(Long.class, ToStringSerializer.instance);
        };
    }
}
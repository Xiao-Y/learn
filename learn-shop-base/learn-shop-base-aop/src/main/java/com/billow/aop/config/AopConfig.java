package com.billow.aop.config;

import com.billow.aop.advice.GlobalExceptionHandler;
import com.billow.aop.advice.GlobalResponseHandler;
import com.billow.aop.aspect.OperationLogsAspect;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.HibernateValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author liuyongtao
 * @since 2021-1-27 14:15
 */
@Configuration
public class AopConfig {


    @Value("${spring.jackson.date-format:yyyy-MM-dd HH:mm:ss}")
    private String pattern;

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

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            // 全局统一 long 转 string
            builder.serializerByType(Long.TYPE, ToStringSerializer.instance);
            builder.serializerByType(Long.class, ToStringSerializer.instance);

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern).withZone(ZoneId.of("GMT+8"));
            //返回时间数据序列化
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
            //接收时间数据反序列化
            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

            // 返回时间数据序列化
            builder.serializerByType(Date.class, new CustomDateSerializer(pattern));
            builder.deserializerByType(Date.class, new CustomDateDeserializer(pattern));
        };
    }
}
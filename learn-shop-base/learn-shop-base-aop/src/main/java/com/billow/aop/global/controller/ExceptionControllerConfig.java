//package com.billow.aop.global.controller;
//
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.boot.autoconfigure.AutoConfigureBefore;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
//import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
//import org.springframework.boot.autoconfigure.web.ResourceProperties;
//import org.springframework.boot.autoconfigure.web.ServerProperties;
//import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
//import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
//import org.springframework.boot.context.properties.EnableConfigurationProperties;
//import org.springframework.boot.web.servlet.error.ErrorAttributes;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.DispatcherServlet;
//
//import javax.servlet.Servlet;
//import java.util.List;
//
///**
// * 用于在还未进入到 controller 中发生的异常
// */
//@Configuration
//@ConditionalOnWebApplication
//@ConditionalOnClass({Servlet.class, DispatcherServlet.class})
//@AutoConfigureBefore(WebMvcAutoConfiguration.class)
//@EnableConfigurationProperties(ResourceProperties.class)
//public class ExceptionControllerConfig {
//
//    private final ServerProperties serverProperties;
//
//    private final List<ErrorViewResolver> errorViewResolvers;
//
//    public ExceptionControllerConfig(ServerProperties serverProperties,
//                                     ObjectProvider<List<ErrorViewResolver>> errorViewResolversProvider) {
//        this.serverProperties = serverProperties;
//        this.errorViewResolvers = errorViewResolversProvider.getIfAvailable();
//    }
//
//    @Bean
//    public GlobalErrorController globalErrorController(ErrorAttributes errorAttributes) {
//        return new GlobalErrorController(errorAttributes, this.serverProperties.getError(), this.errorViewResolvers);
//    }
//}
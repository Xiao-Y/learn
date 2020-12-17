package com.billow.product.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
//import com.billow.alipay.scan.config.AliPayScanConfig;
//import com.billow.alipay.scan.service.AliPayScanService;
//import com.billow.alipay.scan.service.impl.AliPayScanServiceImpl;
import com.billow.mybatis.handler.AuditMetaObjectHandler;
import com.billow.product.properties.AliPayScanProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @create 2019-11-01 9:40
 */
@Configuration
public class BeanConfig {

    /**
     * 审计数据插件
     *
     * @return AuditMetaObjectHandler
     */
    @Bean
    @ConditionalOnMissingBean(name = "auditMetaObjectHandler")
    public AuditMetaObjectHandler auditMetaObjectHandler() {
        return new AuditMetaObjectHandler();
    }

    /**
     * mybatis 分页插件
     *
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     * @author LiuYongTao
     * @date 2019/11/1 10:41
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor page = new PaginationInterceptor();
        page.setDialectType(DbType.MYSQL.getDb());
        return page;
    }

    /**
     * 支付宝，扫码支付
     *
     * @param aliPayScanProperties
     * @return AliPayScanService
     * @author LiuYongTao
     * @date 2019/12/27 10:45
     */
//    @Bean
//    public AliPayScanService aliPayScanService(AliPayScanProperties aliPayScanProperties) {
//        AliPayScanConfig aliPayScanConfig = new AliPayScanConfig();
//        aliPayScanConfig.setAppId(aliPayScanProperties.getAppId());
//        aliPayScanConfig.setAliPayPublicKey(aliPayScanProperties.getAliPayPublicKey());
//        aliPayScanConfig.setPrivateKey(aliPayScanProperties.getPrivateKey());
//        aliPayScanConfig.setGatewayUrl(aliPayScanProperties.getGatewayUrl());
//        aliPayScanConfig.setNotifyUrl(aliPayScanProperties.getNotifyUrl());
//        return new AliPayScanServiceImpl(aliPayScanConfig);
//    }
}

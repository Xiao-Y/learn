package com.billow.product.config;

import org.springframework.context.annotation.Configuration;

//import com.billow.alipay.scan.config.AliPayScanConfig;
//import com.billow.alipay.scan.service.AliPayScanService;
//import com.billow.alipay.scan.service.impl.AliPayScanServiceImpl;

/**
 * @author liuyongtao
 * @create 2019-11-01 9:40
 */
@Configuration
public class BeanConfig {

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

package com.billow.app.config;

import com.billow.tools.constant.CommonCst;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * @author liuyongtao
 * @create 2019-12-12 16:29
 */
@Configuration
public class AppBeanConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        // 以下省略其他相关配置
        RestTemplate restTemplate = new RestTemplate();
        // 使用拦截器包装http header
        restTemplate.setInterceptors(new ArrayList<ClientHttpRequestInterceptor>() {
            {
                add((request, body, execution) -> {
                    String traceId = MDC.get(CommonCst.LOG_TRACE_ID);
                    if (StringUtils.isNotEmpty(traceId)) {
                        request.getHeaders().add(CommonCst.HTTP_HEADER_TRACE_ID, traceId);
                    }
                    return execution.execute(request, body);
                });
            }
        });

        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        // 注意此处需开启缓存，否则会报getBodyInternal方法“getBody not supported”错误
        factory.setBufferRequestBody(true);
        restTemplate.setRequestFactory(factory);
        return new RestTemplate();
    }
}

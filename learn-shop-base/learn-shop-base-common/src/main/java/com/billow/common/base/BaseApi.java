package com.billow.common.base;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;



/**
 * 公用control
 *
 * @author liuyongtao
 * @create 2018-05-19 14:40
 */
@Slf4j
public class BaseApi {

    @Value("${spring.application.name}")
    protected String applicationName;
    //    @Value("${eureka.instance.instance-id}")
    protected String instanceId;

    @Autowired
    protected HttpServletRequest request;


    public String getApplicationName() {
        return applicationName;
    }

    public String getInstanceId() {
        return instanceId;
    }
}

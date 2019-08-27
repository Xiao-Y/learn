package com.billow.base.workflow.config;

import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Configuration;

/**
 * @author liuyongtao
 * @create 2019-08-27 12:44
 */
@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    /**
     * @param springProcessEngineConfiguration - 类为spring boot默认使用的流程引擎配置类
     */
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        //已验证-图片不会出现中文乱码
        springProcessEngineConfiguration.setActivityFontName("宋体");
        springProcessEngineConfiguration.setLabelFontName("宋体");
        springProcessEngineConfiguration.setAnnotationFontName("宋体");
    }
}

package com.billow.base.workflow.config;

import com.billow.base.workflow.component.WorkFlowExecute;
import com.billow.base.workflow.component.WorkFlowQuery;
import com.billow.base.workflow.component.impl.WorkFlowExecuteImpl;
import com.billow.base.workflow.component.impl.WorkFlowQueryImpl;
import com.billow.base.workflow.diagram.ActUtils;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 防止部署时乱码
 *
 * @author liuyongtao
 * @create 2019-08-27 12:44
 */
@Configuration
public class ActivitiConfig implements ProcessEngineConfigurationConfigurer {

    // 中文显示的是口口口，设置字体就好了
    private static final String FONT = "黑体";

    /**
     * @param springProcessEngineConfiguration - 类为spring boot默认使用的流程引擎配置类
     */
    @Override
    public void configure(SpringProcessEngineConfiguration springProcessEngineConfiguration) {
        //图片不会出现中文乱码
        springProcessEngineConfiguration.setActivityFontName(FONT)
                .setLabelFontName(FONT)
                .setAnnotationFontName(FONT);
    }

    @Bean
    public ActUtils actUtils() {
        return new ActUtils();
    }

    @Bean
    public WorkFlowExecute workFlowExecute() {
        return new WorkFlowExecuteImpl();
    }

    @Bean
    public WorkFlowQuery workFlowQuery() {
        return new WorkFlowQueryImpl();
    }
}

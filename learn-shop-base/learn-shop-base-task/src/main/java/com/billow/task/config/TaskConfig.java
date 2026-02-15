package com.billow.task.config;

import com.billow.task.process.TaskProcessService;
import com.billow.task.process.TaskServiceAdapter;
import com.billow.task.process.impl.BatchTaskProcessServiceImpl;
import com.billow.task.service.TaskGroupDetailService;
import com.billow.task.service.TaskGroupService;
import com.billow.task.service.impl.TaskFlowService;
import com.billow.task.service.impl.TaskGroupDetailServiceImpl;
import com.billow.task.service.impl.TaskGroupServiceImpl;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 千面
 * @date 2026-02-06 18:28:59
 */
@Configuration
@MapperScan(basePackages = "com.billow.task.dao")
public class TaskConfig {

    @Bean
    public TaskServiceAdapter taskServiceAdapter(){
        return new TaskServiceAdapter();
    }

    @Bean
    public TaskProcessService batchTaskProcessService() {
        return new BatchTaskProcessServiceImpl();
    }

    @Bean
    public TaskGroupService taskGroupService() {
        return new TaskGroupServiceImpl();
    }

    @Bean
    public TaskGroupDetailService taskGroupDetailService() {
        return new TaskGroupDetailServiceImpl();
    }

    @Bean
    public TaskFlowService taskFlowService() {
        return new TaskFlowService();
    }
}

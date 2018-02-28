package com.ft.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

/**
 * 定时器配置
 *
 * @author liuyongtao
 * @create 2018-02-28 10:37
 */
@Configuration
@EnableScheduling
public class QuartzConfig {

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean() throws IOException {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        //factory.setOverwriteExistingJobs(true);
        // 延时启动,应用启动完10秒后 QuartzScheduler 再启动
        factory.setStartupDelay(10);
        return factory;
    }
}

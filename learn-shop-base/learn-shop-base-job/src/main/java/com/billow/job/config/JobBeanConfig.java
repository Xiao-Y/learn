package com.billow.job.config;

import com.billow.job.core.init.InitJob;
import com.billow.job.core.manager.QuartzManager;
import com.billow.job.dao.ScheduleJobDao;
import com.billow.job.dao.ScheduleJobLogDao;
import com.billow.job.dao.impl.ScheduleJobDaoImpl;
import com.billow.job.dao.impl.ScheduleJobLogDaoImpl;
import com.billow.job.service.CoreAutoTaskService;
import com.billow.job.service.ScheduleJobLogService;
import com.billow.job.service.ScheduleJobService;
import com.billow.job.service.impl.CoreAutoTaskServiceImpl;
import com.billow.job.service.impl.ScheduleJobLogServiceImpl;
import com.billow.job.service.impl.ScheduleJobServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
public class JobBeanConfig {

    @Bean
    @ConditionalOnMissingBean(ScheduleJobDao.class)
    public ScheduleJobDao scheduleJobDaoDefault() {
        return new ScheduleJobDaoImpl();
    }

    @Bean
    @ConditionalOnMissingBean(ScheduleJobLogDao.class)
    public ScheduleJobLogDao scheduleJobLogDaoDefault() {
        return new ScheduleJobLogDaoImpl();
    }

    @Bean
    public CoreAutoTaskService coreAutoTaskService() {
        return new CoreAutoTaskServiceImpl();
    }

    @Bean
    public ScheduleJobLogService scheduleJobLogService() {
        return new ScheduleJobLogServiceImpl();
    }

    @Bean
    public ScheduleJobService scheduleJobService() {
        return new ScheduleJobServiceImpl();
    }

    @Bean
    public QuartzManager quartzManager() {
        return new QuartzManager();
    }

    @Bean
    public InitJob initJob() {
        return new InitJob();
    }

    @Bean
    public ApplicationContextAware applicationContextAware(){
        return new JobApplicationContextAware();
    }
}
package com.billow.job.config;

import com.billow.job.dao.ScheduleJobDao;
import com.billow.job.dao.ScheduleJobLogDao;
import com.billow.job.dao.impl.ScheduleJobDaoImpl;
import com.billow.job.dao.impl.ScheduleJobLogDaoImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
}
package com.billow.job.core.config;

import com.billow.job.service.JobService;
import com.billow.job.service.impl.JobServiceImpl;
import org.quartz.JobListener;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

/**
 * @author liuyongtao
 * @create 2018-03-02 8:35
 */
@Configuration
public class QuartzSchedulerConfig {

    private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerConfig.class);

    private static final String QUARTZ_PROPERTIES_NAME = "/quartz.properties";

    @Bean
    @ConditionalOnMissingBean
    public JobService jobService() {
        return new JobServiceImpl();
    }

    @Bean
    public JobFactory jobFactory() {
        return new AutowiringSpringBeanJobFactory();
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory, Properties quartzProperties) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        try {
            factoryBean.setQuartzProperties(quartzProperties);
            factoryBean.setJobFactory(jobFactory);
            //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除 qrtz_job_details表对应记录了
            factoryBean.setOverwriteExistingJobs(true);
            // 延时启动,应用启动完10秒后 QuartzScheduler 再启动
            factoryBean.setStartupDelay(10);
            // 添加全局监听（用于记录执行日志）
            JobListener monitorJobListener = new MonitorJobListener();
            factoryBean.setGlobalJobListeners(monitorJobListener);
        } catch (Exception e) {
            throw new RuntimeException("生成 SchedulerFactoryBean 异常：", e);
        }

        return factoryBean;
    }

    @Bean
    @DependsOn("jobDataSourceProperties")
    public Properties quartzProperties(JobDataSourceProperties jobDataSourceProperties) throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_NAME));
        propertiesFactoryBean.afterPropertiesSet();
        Properties quartzProperties = propertiesFactoryBean.getObject();

        String dataSourceName = Optional.ofNullable(jobDataSourceProperties.getDataSourceName())
                .orElse(quartzProperties.getProperty("org.quartz.jobStore.dataSource", "jobDataSource"));

        String maxConnections = Optional.ofNullable(jobDataSourceProperties.getMaxConnections())
                .orElse(10)
                .toString();

        quartzProperties.setProperty("org.quartz.jobStore.dataSource", dataSourceName);
        quartzProperties.setProperty("org.quartz.dataSource." + dataSourceName + ".driver", jobDataSourceProperties.getDriver());
        quartzProperties.setProperty("org.quartz.dataSource." + dataSourceName + ".URL", jobDataSourceProperties.getUrl());
        quartzProperties.setProperty("org.quartz.dataSource." + dataSourceName + ".user", jobDataSourceProperties.getUsername());
        quartzProperties.setProperty("org.quartz.dataSource." + dataSourceName + ".password", jobDataSourceProperties.getPassword());
        quartzProperties.setProperty("org.quartz.dataSource." + dataSourceName + ".maxConnections", maxConnections);

        return quartzProperties;
    }
}
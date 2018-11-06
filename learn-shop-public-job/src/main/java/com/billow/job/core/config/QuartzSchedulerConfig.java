package com.billow.job.core.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;
import java.util.Properties;

/**
 * @author liuyongtao
 * @create 2018-03-02 8:35
 */
@Configuration
public class QuartzSchedulerConfig {

    @Autowired
    private DruidDataSource druidDataSource;

    private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerConfig.class);

    private static final String QUARTZ_PROPERTIES_NAME = "/quartz.properties";

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(JobFactory jobFactory) {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        try {
            factoryBean.setQuartzProperties(quartzProperties());
            factoryBean.setDataSource(druidDataSource);
            factoryBean.setJobFactory(jobFactory);
            //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
            factoryBean.setOverwriteExistingJobs(true);
            // 延时启动,应用启动完10秒后 QuartzScheduler 再启动
            factoryBean.setStartupDelay(10);
        } catch (Exception e) {
            logger.error("加载 {} 配置文件失败.", QUARTZ_PROPERTIES_NAME, e);
            throw new RuntimeException("加载配置文件失败", e);
        }

        return factoryBean;
    }

    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(QUARTZ_PROPERTIES_NAME));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
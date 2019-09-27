package com.billow.job.core.config;

import com.billow.job.service.JobService;
import com.billow.job.service.impl.JobServiceImpl;
import org.quartz.JobListener;
import org.quartz.spi.JobFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * @author liuyongtao
 * @create 2018-03-02 8:35
 */
@Configuration
public class QuartzSchedulerConfig {

    @Autowired
    private DataSource dataSource;

    private static final Logger logger = LoggerFactory.getLogger(QuartzSchedulerConfig.class);

    private static final String QUARTZ_PROPERTIES_NAME = "/quartz.properties";

    @Bean
    @ConditionalOnMissingBean
    public JobService jobService() {
        return new JobServiceImpl();
    }

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
            factoryBean.setDataSource(dataSource);
            factoryBean.setJobFactory(jobFactory);
            //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
            factoryBean.setOverwriteExistingJobs(true);
            // 延时启动,应用启动完10秒后 QuartzScheduler 再启动
            factoryBean.setStartupDelay(10);
            // 添加全局监听（用于记录执行日志）
            JobListener monitorJobListener = new MonitorJobListener();
            factoryBean.setGlobalJobListeners(monitorJobListener);
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
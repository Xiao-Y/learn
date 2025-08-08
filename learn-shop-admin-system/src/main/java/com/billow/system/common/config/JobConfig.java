package com.billow.system.common.config;

import com.billow.job.core.config.JobDataSourceProperties;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * 定时任务配置
 *
 * @author 千面
 * @date 2025-08-08 10:30:44
 */
@Configuration
public class JobConfig {


    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;


    @Bean
    @ConfigurationProperties("spring.datasource")
    public JobDataSourceProperties jobDataSourceProperties() throws IOException {
        JobDataSourceProperties properties = new JobDataSourceProperties();
        return properties.setUrl(url)
                .setDriver(driverClassName)
                .setPassword(password)
                .setUsername(username);
    }
}

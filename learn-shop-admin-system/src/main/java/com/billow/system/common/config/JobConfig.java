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


    @Value("${dataSources.master.url}")
    private String url;

    @Value("${dataSources.master.username}")
    private String username;

    @Value("${dataSources.master.password}")
    private String password;

    @Value("${dataSources.master.driverClassName}")
    private String driverClassName;


    @Bean
    public JobDataSourceProperties jobDataSourceProperties() throws IOException {
        JobDataSourceProperties properties = new JobDataSourceProperties();
        return properties.setUrl(url)
                .setDriver(driverClassName)
                .setPassword(password)
                .setUsername(username);
    }
}

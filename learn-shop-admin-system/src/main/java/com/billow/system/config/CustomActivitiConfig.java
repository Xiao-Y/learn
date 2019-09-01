package com.billow.system.config;

import com.billow.system.activiti.CustomGroupEntityManager;
import com.billow.system.activiti.CustomGroupEntityManagerFactory;
import com.billow.system.activiti.CustomUserEntityManager;
import com.billow.system.activiti.CustomUserEntityManagerFactory;
import org.activiti.engine.impl.interceptor.SessionFactory;
import org.activiti.spring.SpringAsyncExecutor;
import org.activiti.spring.SpringProcessEngineConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 防止部署时乱码
 *
 * @author liuyongtao
 * @create 2019-08-27 12:44
 */
//@Configuration
public class CustomActivitiConfig {

//    @Autowired
//    private CustomUserEntityManager customUserEntityManager;
//    @Autowired
//    private CustomGroupEntityManager customGroupEntityManager;
//    @Autowired
//    private CustomUserEntityManagerFactory customUserEntityManagerFactory;
//    @Autowired
//    private CustomGroupEntityManagerFactory customGroupEntityManagerFactory;
//    @Autowired
//    private DataSource dataSource;
//
//    @Primary
//    @Bean("insuranceActivitiConfig")
//    public SpringProcessEngineConfiguration insuranceActivitiConfig(PlatformTransactionManager transactionManager,
//                                                                    SpringAsyncExecutor springAsyncExecutor) throws IOException {
//        SpringProcessEngineConfiguration springProcessEngineConfiguration = new SpringProcessEngineConfiguration();
//        springProcessEngineConfiguration.setDataSource(dataSource);
//        springProcessEngineConfiguration.setTransactionManager(transactionManager);
//        // 配置自定义的用户和组管理
//        springProcessEngineConfiguration.setUserEntityManager(customUserEntityManager);
//        springProcessEngineConfiguration.setGroupEntityManager(customGroupEntityManager);
//
//        List<SessionFactory> customSessionFactories = new ArrayList<>();
//        customSessionFactories.add(customUserEntityManagerFactory);
//        customSessionFactories.add(customGroupEntityManagerFactory);
//        springProcessEngineConfiguration.setCustomSessionFactories(customSessionFactories);
//
//        return springProcessEngineConfiguration;
//    }
}

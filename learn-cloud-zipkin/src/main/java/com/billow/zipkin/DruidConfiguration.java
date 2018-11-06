package com.billow.zipkin;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.SQLException;

/**
 * druid数据源
 *
 * @author liuyongtao
 * @create 2018-02-08 8:43
 */
@Configuration
@ConditionalOnClass(DruidDataSource.class) //该注解的参数对应的类必须存在，否则不解析该注解修饰的配置类
@EnableConfigurationProperties(DruidDSProperties.class) // 用于@Autowired注入
public class DruidConfiguration {

    @Autowired
    private DruidDSProperties druidDSProperties;

    @Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(druidDSProperties.getDriverClassName());
        dataSource.setUrl(druidDSProperties.getZipkinUrl());
        dataSource.setUsername(druidDSProperties.getUsername());
        dataSource.setPassword(druidDSProperties.getPassword());
        dataSource.setInitialSize(druidDSProperties.getInitialSize());
        dataSource.setMinIdle(druidDSProperties.getMinIdle());
        dataSource.setMaxActive(druidDSProperties.getMaxActive());
        dataSource.setMaxWait(druidDSProperties.getMaxWait());
        dataSource.setTestOnReturn(druidDSProperties.getTestOnReturn());
        dataSource.setPoolPreparedStatements(druidDSProperties.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidDSProperties.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setTimeBetweenEvictionRunsMillis(druidDSProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidDSProperties.getMinEvictableIdleTimeMillis());

        dataSource.configFromPropety(druidDSProperties.toProperties());

        DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(druidDSProperties.getZipkinUrl());
        String validationQuery = databaseDriver.getValidationQuery();
        if (validationQuery != null) {
            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery(validationQuery);
        }
        try {
            //开启Druid的监控统计功能，mergeStat代替stat表示sql合并,wall表示防御SQL注入攻击
            dataSource.setFilters("mergeStat,wall,log4j");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }
}

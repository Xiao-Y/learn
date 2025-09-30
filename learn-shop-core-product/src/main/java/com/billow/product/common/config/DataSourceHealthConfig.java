package com.billow.product.common.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.health.HealthContributor;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * 数据源健康检查
 *
 * @author liuyongtao
 * @since 2021-4-14 9:37
 */
@Configuration
public class DataSourceHealthConfig {

    @Value("${spring.datasource.hikari.connection-test-query:select 1}")
    private String defaultQuery;


    @Bean
    public HealthContributor customDataSourceHealthContributor(DataSource dataSource) {
        DataSourceHealthIndicator indicator = new DataSourceHealthIndicator(dataSource);
        if (StringUtils.isEmpty(indicator.getQuery())) {
            indicator.setQuery(defaultQuery);
        }
        return indicator;
    }
}
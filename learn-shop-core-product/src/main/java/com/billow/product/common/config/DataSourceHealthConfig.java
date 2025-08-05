//package com.billow.product.common.config;
//
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.ObjectProvider;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.actuate.autoconfigure.jdbc.DataSourceHealthContributorAutoConfiguration;
//import org.springframework.boot.actuate.health.AbstractHealthIndicator;
//import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
//import org.springframework.boot.jdbc.metadata.DataSourcePoolMetadataProvider;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import java.util.Map;
//
///**
// * 数据源健康检查
// *
// * @author liuyongtao
// * @since 2021-4-14 9:37
// */
//@Configuration
//public class DataSourceHealthConfig extends DataSourceHealthContributorAutoConfiguration {
//
//    @Value("${spring.datasource.hikari.connection-test-query:select 1}")
//    private String defaultQuery;
//
//
//    public DataSourceHealthConfig(Map<String, DataSource> dataSources, ObjectProvider<DataSourcePoolMetadataProvider> metadataProviders) {
//        super(dataSources, metadataProviders);
//    }
//
//    @Override
//    protected AbstractHealthIndicator createIndicator(DataSource source) {
//        DataSourceHealthIndicator indicator = (DataSourceHealthIndicator) super.createIndicator(source);
//        if (StringUtils.isEmpty(indicator.getQuery())) {
//            indicator.setQuery(defaultQuery);
//        }
//        return indicator;
//    }
//}
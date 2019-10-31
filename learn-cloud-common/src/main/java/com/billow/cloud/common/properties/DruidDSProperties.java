package com.billow.cloud.common.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Properties;

/**
 * Druid 配置属性
 */
@Data
@ConfigurationProperties(prefix = "druid.datasource")
public class DruidDSProperties {
    private String type;
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private Integer initialSize;
    private Integer minIdle;
    private Integer maxActive;
    private Integer maxWait;
    private Integer timeBetweenEvictionRunsMillis;
    private Integer minEvictableIdleTimeMillis;
    private String validationQuery;
    private Boolean testWhileIdle;
    private Boolean testOnBorrow;
    private Boolean testOnReturn;
    private Boolean poolPreparedStatements;
    private Integer maxPoolPreparedStatementPerConnectionSize;
    private String filters;


    public Properties toProperties() {
        Properties properties = new Properties();
        notNullAdd(properties, "testWhileIdle", this.testWhileIdle);
        notNullAdd(properties, "testOnBorrow", this.testOnBorrow);
        notNullAdd(properties, "validationQuery", this.validationQuery);
        notNullAdd(properties, "filters", this.filters);

        notNullAdd(properties, "initialSize", this.initialSize);
        notNullAdd(properties, "minIdle", this.minIdle);
        notNullAdd(properties, "maxActive", this.maxActive);
        notNullAdd(properties, "maxWait", this.maxWait);
        notNullAdd(properties, "testOnReturn", this.testOnReturn);
        notNullAdd(properties, "timeBetweenEvictionRunsMillis", this.timeBetweenEvictionRunsMillis);
        notNullAdd(properties, "poolPreparedStatements", this.poolPreparedStatements);
        notNullAdd(properties, "maxPoolPreparedStatementPerConnectionSize", this.maxPoolPreparedStatementPerConnectionSize);
        notNullAdd(properties, "minEvictableIdleTimeMillis", this.minEvictableIdleTimeMillis);
        return properties;
    }

    private void notNullAdd(Properties properties, String key, Object value) {
        if (value != null) {
            properties.setProperty("druid." + key, value.toString());
        }
    }
}

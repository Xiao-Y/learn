package com.billow.job.core.config;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * job 数据源配置
 *
 * @author 千面
 * @date 2025-08-08 11:01:56
 */
@Data
@Accessors(chain = true)
public class JobDataSourceProperties {

    /**
     * 数据源名称，默认为 jobDataSource
     */
    private String dataSourceName;
    /**
     * 数据源驱动
     */
    private String driver;
    /**
     * 数据源连接地址
     */
    private String url;
    /**
     * 数据源用户名
     */
    private String username;
    /**
     * 数据源密码
     */
    private String password;
    /**
     * 数据源最大连接数，默认为 10
     */
    private Integer maxConnections;

}

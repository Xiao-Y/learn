package com.billow.excel.config;

import com.billow.excel.excelKet.ExcelCore;
import com.billow.excel.excelKet.ExcelKit;
import com.billow.excel.provider.impl.DatabaseDictProvider;
import com.billow.excel.provider.impl.EnumDictProvider;
import com.billow.excel.provider.impl.RedisDictProvider;
import com.billow.excel.service.DictService;
import com.billow.excel.service.ExcelTaskService;
import com.billow.excel.service.impl.CompositeDictService;
import com.billow.excel.service.impl.ExcelTaskServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * Excel自动配置类
 */
@Configuration
@EnableConfigurationProperties(ExcelProperties.class)
public class ExcelAutoConfig {

    /**
     * 配置枚举字典提供者
     */
    @Bean
    public EnumDictProvider enumDictProvider(ExcelProperties properties) {
        return new EnumDictProvider(properties);
    }

    /**
     * 配置数据库字典提供者
     */
    @Bean
    public DatabaseDictProvider databaseDictProvider(JdbcTemplate jdbcTemplate) {
        return new DatabaseDictProvider(jdbcTemplate);
    }

    /**
     * 配置Redis字典提供者
     */
    @Bean
    public RedisDictProvider redisDictProvider(StringRedisTemplate redisTemplate, ExcelProperties properties) {
        return new RedisDictProvider(redisTemplate, properties);
    }

    /**
     * 配置字典服务
     */
    @Bean
    public DictService compositeDictService() {
        return new CompositeDictService();
    }

    /**
     * 配置任务服务
     */
    @Bean
    public ExcelTaskService excelTaskService(JdbcTemplate jdbcTemplate) {
        return new ExcelTaskServiceImpl(jdbcTemplate);
    }

    /**
     * 配置任务服务
     */
    @Bean
    public ExcelCore excelCore(DictService dictService) {
        return new ExcelCore(dictService);
    }

    /**
     * 配置Excel导出器
     */
    @Bean
    public ExcelKit excelKit(ExcelTaskService taskService, ExcelCore excelCore) {
        return new ExcelKit(excelCore, taskService);
    }
}

package com.billow.excel.autoconfigure;

import com.billow.excel.core.DefaultExcelExporter;
import com.billow.excel.core.DefaultExcelImporter;
import com.billow.excel.core.ExcelExporter;
import com.billow.excel.core.ExcelImporter;
import com.billow.excel.service.DictService;
import com.billow.excel.service.ExcelTaskService;
import com.billow.excel.service.impl.CompositeDictService;
import com.billow.excel.service.impl.DatabaseDictProvider;
import com.billow.excel.service.impl.EnumDictProvider;
import com.billow.excel.service.impl.ExcelTaskServiceImpl;
import com.billow.excel.service.impl.RedisDictProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
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
public class ExcelAutoConfiguration {

    /**
     * 配置任务服务
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JdbcTemplate.class)
    public ExcelTaskService excelTaskService(JdbcTemplate jdbcTemplate) {
        return new ExcelTaskServiceImpl(jdbcTemplate);
    }

    /**
     * 配置字典服务
     */
    @Bean
    @ConditionalOnMissingBean
    public CompositeDictService compositeDictService() {
        return new CompositeDictService();
    }

    /**
     * 配置枚举字典提供者
     */
    @Bean
    @ConditionalOnMissingBean
    public EnumDictProvider enumDictProvider(ExcelProperties properties) {
        return new EnumDictProvider(properties);
    }

    /**
     * 配置数据库字典提供者
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(JdbcTemplate.class)
    public DatabaseDictProvider databaseDictProvider(JdbcTemplate jdbcTemplate) {
        return new DatabaseDictProvider(jdbcTemplate);
    }

    /**
     * 配置Redis字典提供者
     */
    @Bean
    @ConditionalOnBean(StringRedisTemplate.class)
    @ConditionalOnMissingBean
    public RedisDictProvider redisDictProvider(StringRedisTemplate redisTemplate, ExcelProperties properties) {
        return new RedisDictProvider(redisTemplate, properties);
    }

    /**
     * 配置Excel导出器
     */
    @Bean
    @ConditionalOnMissingBean
    public ExcelExporter excelExporter(ExcelTaskService taskService, DictService dictService) {
        return new DefaultExcelExporter(dictService, taskService);
    }

    /**
     * 配置Excel导入器
     */
    @Bean
    @ConditionalOnMissingBean
    public ExcelImporter excelImporter(DictService dictService) {
        return new DefaultExcelImporter(dictService);
    }
} 
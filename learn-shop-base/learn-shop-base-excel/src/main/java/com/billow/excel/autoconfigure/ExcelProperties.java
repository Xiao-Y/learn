package com.billow.excel.autoconfigure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Excel配置属性
 */
@Data
@ConfigurationProperties(prefix = "learn.excel")
public class ExcelProperties {

    /**
     * 字典配置
     */
    private Dict dict = new Dict();

    /**
     * 字典配置
     */
    @Data
    public static class Dict {
        /**
         * Redis字典前缀
         */
        private String redisKeyPrefix = "DICT:";

        /**
         * 是否启用枚举字典自动扫描
         */
        private boolean enableEnumScan = true;

        /**
         * 枚举字典扫描包路径
         */
        private String[] enumScanPackages = {};
    }
} 
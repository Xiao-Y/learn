package com.billow.excel.annotation;

import java.lang.annotation.*;

/**
 * Excel 列注解
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelColumn {
    /**
     * 列名
     */
    String name();

    /**
     * 列顺序
     */
    int order() default 0;

    /**
     * 列宽（字符数）
     */
    int width() default 20;

    /**
     * 日期/数字格式化
     */
    String format() default "";

    /**
     * 数据字典编码
     */
    String dictCode() default "";

    /**
     * 字典提供者类型
     * ENUM: 枚举字典
     * DATABASE: 数据库字典
     * REDIS: Redis字典
     */
    DictType dictType() default DictType.NULL;

    /**
     * 是否必填
     */
    boolean required() default false;

    /**
     * 自定义校验器类
     */
    Class<?> validator() default Void.class;

    /**
     * 自定义转换器类
     */
    Class<?> converter() default Void.class;

    /**
     * 字典提供者类型枚举
     */
    enum DictType {
        /**
         * 空
         */
        NULL,

        /**
         * 枚举字典
         */
        ENUM,

        /**
         * 数据库字典
         */
        DATABASE,

        /**
         * Redis字典
         */
        REDIS
    }
} 

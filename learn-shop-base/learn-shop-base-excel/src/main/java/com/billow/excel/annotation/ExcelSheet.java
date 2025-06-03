package com.billow.excel.annotation;

import java.lang.annotation.*;

/**
 * Excel 工作表注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExcelSheet {
    /**
     * 工作表名称
     */
    String name() default "";

    /**
     * 工作表描述
     */
    String description() default "";

    /**
     * 表头颜色（十六进制）
     */
    String headColor() default "#FFFFFF";

    /**
     * 是否冻结首行
     */
    boolean frozen() default true;

    /**
     * 数据开始行号（从0开始）
     */
    int index() default 1;
} 
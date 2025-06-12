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
     * 冻结行数，设置为0 表示不冻结
     *
     * @author 千面
     */
    int freezeRow() default 1;

    /**
     * 数据开始行号（从0开始）
     */
    int headerIndex() default 1;

    /**
     * 起始sheet页的下标
     *
     * @param
     * @return int
     * @author 千面
     * @date 2023/11/24 10:03
     */
    int startSheetIndex() default 1;
} 
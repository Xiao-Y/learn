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
    String title();

    /**
     * 列宽（字符数）
     */
    int width() default 0;

    /**
     * 日期/数字格式化
     */
    String format() default "";

    /**
     * 是否必填
     */
    boolean required() default false;

    /**
     * 提示信息
     */
    String message() default "";

    /**
     * 数据字典
     *
     * @param
     * @return Class<Annotation>
     * @author 千面
     * @date 2023/11/24 10:32
     */
    ExcelDict dict() default @ExcelDict();
}

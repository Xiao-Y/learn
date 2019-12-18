package com.billow.tools.excel;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * excel文件导入导出的表头
 *
 * @author LiuYongTao
 * @date 2018/7/10 8:39
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Title {

    /**
     * 标题的名称
     *
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2018/7/10 8:45
     */
    String name() default "";

    /**
     * 标题排序
     *
     * @return int
     * @author LiuYongTao
     * @date 2018/7/10 8:45
     */
    double order() default 99999;

    /**
     * 当是 name 为空时，否使用属性名称作为标题，默认为 true
     *
     * @return boolean
     * @author LiuYongTao
     * @date 2019/5/28 11:49
     */
    boolean isField() default true;
}

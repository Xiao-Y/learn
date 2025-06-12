package com.billow.excel.annotation;

import com.billow.excel.converter.ExcelCover;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 通过字段选项组进行字段翻译
 *
 * @author 千面
 * @date 2023/11/24 10:24
 */
@Target({})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelDict {
    /**
     * 模板
     *
     * @author 千面
     * @date 2023/11/27 9:51
     */
    String TEMPLATE = "template";

    /**
     * 导入
     *
     * @author 千面
     * @date 2023/11/27 9:51
     */
    String IMPORT = "import";

    /**
     * 导出
     *
     * @author 千面
     * @date 2023/11/27 9:51
     */
    String EXPORT = "export";

    /**
     * 数据字典编码
     */
    String dictCode() default "";

    /**
     * 字典提供者类型
     * ENUM: 枚举字典
     * DATABASE: 数据库字典
     * REDIS: Redis字典（默认）
     */
    DictType dictType() default DictType.REDIS;

    /**
     * 翻译后的数据存放位置（如果为空，会放入当前字段）
     *
     * @param
     * @return String
     * @author 千面
     * @date 2023/11/24 10:25
     */
    String tranName() default "";

    /**
     * 如果选项组不能满足时，可以手动指定转换
     *
     * @param
     * @return Class<ExcelCover>
     * @author 千面
     * @date 2023/11/24 10:46
     */
    Class<? extends ExcelCover> coverClass() default ExcelCover.class;


    /**
     * 字典提供者类型枚举
     */
    enum DictType {
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
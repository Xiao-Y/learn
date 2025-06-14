package com.billow.excel.model;

import com.billow.excel.annotation.ExcelDict;
import lombok.Data;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * excel 模型对象
 *
 * @author 千面
 * @date 2023/11/24 13:45
 */
@Data
public class ExcelColumnModel {

    /**
     * 是否必填字段
     *
     * @param
     * @return boolean
     * @author 千面
     * @date 2023/11/27 9:38
     */
    private boolean required;

    /**
     * 属性字段
     *
     * @author 千面
     * @date 2023/11/24 13:44
     */
    private Field field;

    /**
     * 列名值
     **/
    private String title;

    /**
     * 列宽（字符数）
     **/
    private int width;

    /**
     * 翻译后的数据存放位置（如果为空，会放入当前字段）
     *
     * @param
     * @return String
     * @author 千面
     * @date 2023/11/24 10:25
     */
    private Field tranField;

    /**
     * 日期时间格式
     **/
    private String format;

    /**
     * 提示信息
     **/
    private String message;

    /**
     * 数据字典
     */
    private ExcelDict dict;

    /**
     * 字典数据
     *
     * @author 千面
     * @date 2023/11/24 11:37
     */
    private Map<String, String> dictMap;

}

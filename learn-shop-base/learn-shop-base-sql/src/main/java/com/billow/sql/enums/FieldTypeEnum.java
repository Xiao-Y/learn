package com.billow.sql.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 字段类型枚举
 *
 * @author 千面
 * @date 2022/8/3 10:51
 */
@Getter
@AllArgsConstructor
public enum FieldTypeEnum
{
    CHAR("char", "字符"),
    VARCHAR("varchar", "字符串"),
    TINYINT("tinyint", "小整数"),
    INT("int", "大整数"),
    BIGINT("bigint", "极大整数"),
    DOUBLE("double", "双精度浮点数"),
    DECIMAL("decimal", "小数"),
    DATE_TIME("datetime", "日期"),
    DATE("date", "日期date类型"),
    TEXT("text", "文本"),
    BLOB("blob", "字节"),
    MEDIUM_TEXT("mediumtext", "文本");

    /**
     * 编码
     */
    private String code;

    /**
     * 描述
     */
    private String desc;

}

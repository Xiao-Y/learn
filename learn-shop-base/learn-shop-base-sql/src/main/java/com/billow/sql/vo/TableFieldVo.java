package com.billow.sql.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.sql.Types;

/**
 * 字段信息对象
 *
 * @author 千面
 * @date 2022/8/3 17:08
 */
@Data
@ToString
@EqualsAndHashCode
@Accessors(chain = true)
public class TableFieldVo
{

    private static final long serialVersionUID = 1L;

    /**
     * 字段名称
     */
    private String dbFieldName;


    /**
     * 字段类型, {@link Types}
     */
    private int dbType;


    /**
     * 查询条件
     */
    private Object objValue;
}

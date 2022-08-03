package com.billow.sql.vo;

import com.billow.sql.enums.FieldTypeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

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
     * 字段类型, varchar、bigint、double、decimal、datetime、text、blob
     */
    private FieldTypeEnum dbType;


    /**
     * 查询条件
     */
    private Object objValue;
}

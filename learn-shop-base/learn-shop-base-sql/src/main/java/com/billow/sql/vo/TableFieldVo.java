package com.billow.sql.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * @program： DMP(数据管理平台)
 * @description： 字段信息对象 dmp_table_field
 * @author: 忘机
 * @date: 2022-01-10
 * @company： 深圳减字科技有限公司
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
     * 字段备注
     */
    private String dbFieldRemark;

    /**
     * 字段旧名称
     */
    private String dbFieldNameOld;

    /**
     * 是否是主键, false:不是，true：是
     */
    private Boolean dbIsKey;

    /**
     * 是否可为空，false：不可以，true：可以
     */
    private Boolean dbIsNull;

    /**
     * 字段类型, varchar、bigint、double、decimal、datetime、text、blob
     */
    private String dbType;

    /**
     * 长度
     */
    private Integer dbLength;

    /**
     * 小数点
     */
    private Integer dbPointLength;

    /**
     * 默认值
     * 默认值必须支持置为NULL
     */
    private String dbDefaultVal;


    /**
     * 是否是字符串类型
     */
    @JsonIgnore
    private Boolean typeIsVar;

    /**
     * 是否有默认值
     */
    private Boolean haveDefaultValue;


    /**
     * 查询条件
     */
    private Object objValue;
}

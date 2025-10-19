package com.billow.system.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 *
 * </p>
 *
 * @author billow
 * @since 2021-04-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("sys_data_dictionary")
@Schema(title = "DataDictionaryPo对象", description = "")
public class DataDictionaryPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Column("field_type")
    private String fieldType;

    @Column("field_value")
    private String fieldValue;

    @Column("field_display")
    private String fieldDisplay;

    @Column("system_module")
    private String systemModule;

    @Column("field_order")
    private Integer fieldOrder;

    @Column("description")
    private String description;


}

package com.billow.system.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import java.io.Serial;
import java.io.Serializable;

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
 * @since 2025-10-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("sys_data_dictionary")
@Schema(title = "DataDictionaryPo对象", description="")
public class DataDictionaryPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @TableField("field_type")
    private String fieldType;

    @TableField("field_value")
    private String fieldValue;

    @TableField("field_display")
    private String fieldDisplay;

    @TableField("system_module")
    private String systemModule;

    @TableField("field_order")
    private Integer fieldOrder;

    @TableField("description")
    private String description;


}

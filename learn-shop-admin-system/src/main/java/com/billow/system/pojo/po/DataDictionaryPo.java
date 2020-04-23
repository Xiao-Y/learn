package com.billow.system.pojo.po;

import com.billow.jpa.base.pojo.BasePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 数据字典
 *
 * @author liuyongtao
 * @create 2019-07-11 10:47
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "sys_data_dictionary")
public class DataDictionaryPo extends BasePo implements Serializable {

    @ApiModelProperty("系统模块")
    private String systemModule;

    @ApiModelProperty("下拉字段分类")
    private String fieldType;

    @ApiModelProperty("显示的名称")
    private String fieldDisplay;

    @ApiModelProperty("显示名称的值")
    private String fieldValue;

    @ApiModelProperty("字段排序")
    private Integer fieldOrder;

    @ApiModelProperty("说明")
    private String descritpion;
}

package com.billow.common.business.ex;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 下拉列表组件模型
 *
 * @author liuyongtao
 * @create 2019-12-05 11:05
 */
@Data
public class SelectEx {

    @ApiModelProperty("名称id")
    private String id;

    @ApiModelProperty("显示的名称")
    private String fieldDisplay;

    @ApiModelProperty("显示名称的值")
    private String fieldValue;

    @ApiModelProperty("字段排序")
    private Long fieldOrder;
}

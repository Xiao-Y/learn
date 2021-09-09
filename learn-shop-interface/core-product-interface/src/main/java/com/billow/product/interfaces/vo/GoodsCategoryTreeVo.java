package com.billow.product.interfaces.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * app 端 分类树
 *
 * @author liuyongtao
 * @since 2021-9-9 8:47
 */
@Data
public class GoodsCategoryTreeVo {

    @ApiModelProperty(value = "分类id")
    private Long id;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    @ApiModelProperty(value = "禁用选项")
    private Boolean validInd;
}

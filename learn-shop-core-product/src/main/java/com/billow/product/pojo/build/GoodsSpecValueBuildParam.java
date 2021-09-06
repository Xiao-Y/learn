package com.billow.product.pojo.build;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 规格值表 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsSpecValueBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规格id")
    private Long specKeyId;

    @ApiModelProperty(value = "规格值")
    private String specValue;

    @ApiModelProperty(value = "规格排序")
    private Long valueSort;


}

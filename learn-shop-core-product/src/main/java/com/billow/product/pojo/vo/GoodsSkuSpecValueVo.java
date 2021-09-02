package com.billow.product.pojo.vo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * sku规格值 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Data
@Accessors(chain = true)
public class GoodsSkuSpecValueVo implements Serializable {

    @ApiModelProperty(value = "sku_id")
    private Long skuId;

    @ApiModelProperty(value = "商品id")
    private Long spuId;

    @ApiModelProperty(value = "规格id(冗余)")
    private Long specKeyId;

    @ApiModelProperty(value = "规格值id")
    private Long specValueId;

    @ApiModelProperty(value = "规格值排序")
    private Long skuSpecSort;

    @ApiModelProperty(value = "规格值")
    private String specValue;
}

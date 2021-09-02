package com.billow.product.pojo.build;

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
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsSkuSpecValueBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

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


}

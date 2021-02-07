package com.billow.product.pojo.ex;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * 商品信息
 *
 * @author liuyongtao
 * @since 2021-2-7 17:23
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class SpuInfoEx {

    @ApiModelProperty(value = "spu_id")
    private Long spuId;

    // GoodsBrandPo
    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    // GoodsCategoryPo
    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    // GoodsSpuPo
    @ApiModelProperty(value = "商品编号，唯一")
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "最低售价")
    private Integer lowPrice;

    @ApiModelProperty(value = "总库存量")
    private Long stock;

}

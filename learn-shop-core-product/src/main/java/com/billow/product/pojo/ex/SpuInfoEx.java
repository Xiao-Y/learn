package com.billow.product.pojo.ex;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "spu_id")
    private Long spuId;

    // GoodsBrandPo
    @Schema(title = "品牌id")
    private Long brandId;

    @Schema(title = "品牌名称")
    private String brandName;

    // GoodsCategoryPo
    @Schema(title = "分类id")
    private Long categoryId;

    @Schema(title = "分类名称")
    private String categoryName;

    // GoodsSpuPo
    @Schema(title = "商品编号，唯一")
    private String spuNo;

    @Schema(title = "商品名称")
    private String goodsName;

    @Schema(title = "子标题")
    private String subTitle;

    @Schema(title = "最低售价")
    private Integer lowPrice;

    @Schema(title = "总库存量")
    private Long stock;

}

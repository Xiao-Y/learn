package com.billow.search.pojo;

import io.swagger.annotations.ApiModelProperty;

public class Product {

    // GoodsBrandPo
    @ApiModelProperty(value = "品牌id")
    private String brandId;
    @ApiModelProperty(value = "品牌名称")
    private String brandName;

    // GoodsCategoryPo
    @ApiModelProperty(value = "分类id")
    private String categoryId;
    @ApiModelProperty(value = "分类名称")
    private String categoryName;

    // ShopInfoPo
    @ApiModelProperty(value = "商铺id,为0表示自营")
    private String shopId;
    @ApiModelProperty(value = "店铺名称")
    private String shopName;

    // GoodsSpuPo
    @ApiModelProperty(value = "spu_id")
    private String spuId;
    @ApiModelProperty(value = "商品编号，唯一")
    private String spuNo;
    @ApiModelProperty(value = "商品名称")
    private String goodsName;
    @ApiModelProperty(value = "最低售价")
    private Integer lowPrice;
    @ApiModelProperty(value = "总库存量")
    private Long stock;

    // GoodsSpecKeyPo
    @ApiModelProperty(value = "规格id")
    private String specKeyId;
    @ApiModelProperty(value = "规格名称")
    private String specName;

    // GoodsSpecValuePo
    @ApiModelProperty(value = "规格值")
    private String specValue;

}

package com.billow.cart.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车视图对象
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Data
@Accessors(chain = true)
public class CartVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "购物车ID")
    private Long id;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "SKU ID")
    private Long skuId;

    @ApiModelProperty(value = "商品数量")
    private Integer quantity;

    @ApiModelProperty(value = "是否选中")
    private Boolean selected;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品图片")
    private String goodsImage;

    @ApiModelProperty(value = "商品价格")
    private Integer price;

    @ApiModelProperty(value = "商品规格")
    private String spec;

    @ApiModelProperty(value = "购物车商品列表")
    private List<CartItemVo> items;
} 
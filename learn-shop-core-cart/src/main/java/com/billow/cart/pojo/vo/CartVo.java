package com.billow.cart.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "购物车ID")
    private Long id;

    @Schema(title = "用户ID")
    private Long userId;

    @Schema(title = "SKU ID")
    private Long skuId;

    @Schema(title = "商品数量")
    private Integer quantity;

    @Schema(title = "是否选中")
    private Boolean selected;

    @Schema(title = "商品名称")
    private String goodsName;

    @Schema(title = "商品图片")
    private String goodsImage;

    @Schema(title = "商品价格")
    private Integer price;

    @Schema(title = "商品规格")
    private String spec;

    @Schema(title = "购物车商品列表")
    private List<CartItemVo> items;
} 
package com.billow.cart.pojo.build;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 购物车构建参数
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Data
@Accessors(chain = true)
public class CartBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "用户ID")
    private Long userId;

    @Schema(title = "SKU ID")
    private Long skuId;

    @Schema(title = "商品数量")
    private Integer quantity;

    @Schema(title = "是否选中")
    private Boolean selected;
} 
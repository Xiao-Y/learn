package com.billow.cart.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 购物车实体
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("cart")
@Schema(title = "CartPo对象", description="购物车")
public class CartPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "用户ID")
    @Column("user_id")
    private Long userId;

    @Schema(title = "SKU ID")
    @Column("sku_id")
    private Long skuId;

    @Schema(title = "商品数量")
    @Column("quantity")
    private Integer quantity;

    @Schema(title = "是否选中")
    @Column("selected")
    private Boolean selected;
} 
package com.billow.cart.pojo.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 购物车商品表
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@Table("cart_item")
@Schema(title = "CartItem对象", description = "购物车商品表")
public class CartItem extends BasePo {

    @Schema(title = "租户ID")
    @Column("tenant_id")
    private Long tenantId;

    @Schema(title = "购物车ID")
    @Column("cart_id")
    private Long cartId;

    @Schema(title = "用户ID")
    @Column("user_id")
    private Long userId;

    @Schema(title = "商品ID")
    @Column("product_id")
    private Long productId;

    @Schema(title = "SKU ID")
    @Column("sku_id")
    private Long skuId;

    @Schema(title = "商品数量")
    @Column("quantity")
    private Integer quantity;

    @Schema(title = "原始价格")
    @Column("original_price")
    private BigDecimal originalPrice;

    @Schema(title = "销售价格")
    @Column("sale_price")
    private BigDecimal salePrice;

    @Schema(title = "小计金额")
    @Column("subtotal")
    private BigDecimal subtotal;

    @Schema(title = "是否选中：0-未选中，1-已选中")
    @Column("selected")
    private Boolean selected;

    @Schema(title = "状态：1-正常，2-已失效，3-已下单")
    @Column("status")
    private Integer status;

    @Schema(title = "失效原因")
    @Column("invalid_reason")
    private String invalidReason;

    @Schema(title = "乐观锁版本号")
    @Column("version")
    private Integer version;

    @Schema(title = "商品名称")
    @Column("product_name")
    private String productName;

    @Schema(title = "SKU名称")
    @Column("sku_name")
    private String skuName;

    @Schema(title = "SKU规格值JSON")
    @Column("sku_spec_values")
    private String skuSpecValues;

    @Schema(title = "商品图片")
    @Column("product_pic")
    private String productPic;

    @Schema(title = "促销活动ID")
    @Column("promotion_id")
    private Long promotionId;

    @Schema(title = "促销类型：1-满减，2-折扣，3-秒杀")
    @Column("promotion_type")
    private Integer promotionType;

    @Schema(title = "促销优惠金额")
    @Column("promotion_amount")
    private BigDecimal promotionAmount;

    @Schema(title = "备注")
    @Column("remark")
    private String remark;
} 
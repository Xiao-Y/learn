package com.billow.cart.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("cart_item")
@Schema(title = "CartItem对象", description = "购物车商品表")
public class CartItem extends BasePo {

    @Schema(title = "租户ID")
    @TableField("tenant_id")
    private Long tenantId;

    @Schema(title = "购物车ID")
    @TableField("cart_id")
    private Long cartId;

    @Schema(title = "用户ID")
    @TableField("user_id")
    private Long userId;

    @Schema(title = "商品ID")
    @TableField("product_id")
    private Long productId;

    @Schema(title = "SKU ID")
    @TableField("sku_id")
    private Long skuId;

    @Schema(title = "商品数量")
    @TableField("quantity")
    private Integer quantity;

    @Schema(title = "原始价格")
    @TableField("original_price")
    private BigDecimal originalPrice;

    @Schema(title = "销售价格")
    @TableField("sale_price")
    private BigDecimal salePrice;

    @Schema(title = "小计金额")
    @TableField("subtotal")
    private BigDecimal subtotal;

    @Schema(title = "是否选中：0-未选中，1-已选中")
    @TableField("selected")
    private Boolean selected;

    @Schema(title = "状态：1-正常，2-已失效，3-已下单")
    @TableField("status")
    private Integer status;

    @Schema(title = "失效原因")
    @TableField("invalid_reason")
    private String invalidReason;

    @Schema(title = "乐观锁版本号")
    @TableField("version")
    private Integer version;

    @Schema(title = "商品名称")
    @TableField("product_name")
    private String productName;

    @Schema(title = "SKU名称")
    @TableField("sku_name")
    private String skuName;

    @Schema(title = "SKU规格值JSON")
    @TableField("sku_spec_values")
    private String skuSpecValues;

    @Schema(title = "商品图片")
    @TableField("product_pic")
    private String productPic;

    @Schema(title = "促销活动ID")
    @TableField("promotion_id")
    private Long promotionId;

    @Schema(title = "促销类型：1-满减，2-折扣，3-秒杀")
    @TableField("promotion_type")
    private Integer promotionType;

    @Schema(title = "促销优惠金额")
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    @Schema(title = "备注")
    @TableField("remark")
    private String remark;
} 
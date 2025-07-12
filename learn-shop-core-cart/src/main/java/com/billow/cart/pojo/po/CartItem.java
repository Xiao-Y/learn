package com.billow.cart.pojo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel(value = "CartItem对象", description = "购物车商品表")
public class CartItem extends BasePo {

    @ApiModelProperty("租户ID")
    @TableField("tenant_id")
    private Long tenantId;

    @ApiModelProperty("购物车ID")
    @TableField("cart_id")
    private Long cartId;

    @ApiModelProperty("用户ID")
    @TableField("user_id")
    private Long userId;

    @ApiModelProperty("商品ID")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty("SKU ID")
    @TableField("sku_id")
    private Long skuId;

    @ApiModelProperty("商品数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty("原始价格")
    @TableField("original_price")
    private BigDecimal originalPrice;

    @ApiModelProperty("销售价格")
    @TableField("sale_price")
    private BigDecimal salePrice;

    @ApiModelProperty("小计金额")
    @TableField("subtotal")
    private BigDecimal subtotal;

    @ApiModelProperty("是否选中：0-未选中，1-已选中")
    @TableField("selected")
    private Boolean selected;

    @ApiModelProperty("状态：1-正常，2-已失效，3-已下单")
    @TableField("status")
    private Integer status;

    @ApiModelProperty("失效原因")
    @TableField("invalid_reason")
    private String invalidReason;

    @ApiModelProperty("乐观锁版本号")
    @TableField("version")
    private Integer version;

    @ApiModelProperty("商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty("SKU名称")
    @TableField("sku_name")
    private String skuName;

    @ApiModelProperty("SKU规格值JSON")
    @TableField("sku_spec_values")
    private String skuSpecValues;

    @ApiModelProperty("商品图片")
    @TableField("product_pic")
    private String productPic;

    @ApiModelProperty("促销活动ID")
    @TableField("promotion_id")
    private Long promotionId;

    @ApiModelProperty("促销类型：1-满减，2-折扣，3-秒杀")
    @TableField("promotion_type")
    private Integer promotionType;

    @ApiModelProperty("促销优惠金额")
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    @ApiModelProperty("备注")
    @TableField("remark")
    private String remark;
} 
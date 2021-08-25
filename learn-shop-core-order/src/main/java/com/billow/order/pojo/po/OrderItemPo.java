package com.billow.order.pojo.po;

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
 * <p>
 * 
 * </p>
 *
 * @author billow
 * @since 2021-08-23
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("oms_order_item")
@ApiModel(value="OrderItemPo对象", description="")
public class OrderItemPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "订单id")
    @TableField("order_id")
    private Long orderId;

    @ApiModelProperty(value = "订单编号")
    @TableField("order_sn")
    private String orderSn;

    @ApiModelProperty(value = "商品id")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty(value = "商品图片")
    @TableField("product_pic")
    private String productPic;

    @ApiModelProperty(value = "商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty(value = "商品品牌")
    @TableField("product_brand")
    private String productBrand;

    @ApiModelProperty(value = "商品条码")
    @TableField("product_sn")
    private String productSn;

    @ApiModelProperty(value = "销售价格")
    @TableField("product_price")
    private BigDecimal productPrice;

    @ApiModelProperty(value = "购买数量")
    @TableField("product_quantity")
    private Integer productQuantity;

    @ApiModelProperty(value = "商品sku编号")
    @TableField("product_sku_id")
    private Long productSkuId;

    @ApiModelProperty(value = "商品sku条码")
    @TableField("product_sku_code")
    private String productSkuCode;

    @ApiModelProperty(value = "商品分类id")
    @TableField("product_category_id")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品的销售属性1")
    @TableField("sp1")
    private String sp1;

    @ApiModelProperty(value = "商品的销售属性2")
    @TableField("sp2")
    private String sp2;

    @ApiModelProperty(value = "商品的销售属性3")
    @TableField("sp3")
    private String sp3;

    @ApiModelProperty(value = "商品促销名称")
    @TableField("promotion_name")
    private String promotionName;

    @ApiModelProperty(value = "商品促销分解金额")
    @TableField("promotion_amount")
    private BigDecimal promotionAmount;

    @ApiModelProperty(value = "优惠券优惠分解金额")
    @TableField("coupon_amount")
    private BigDecimal couponAmount;

    @ApiModelProperty(value = "积分优惠分解金额")
    @TableField("integration_amount")
    private BigDecimal integrationAmount;

    @ApiModelProperty(value = "该商品经过优惠后的分解金额")
    @TableField("real_amount")
    private BigDecimal realAmount;

    @ApiModelProperty(value = "商品赠送积分")
    @TableField("gift_integration")
    private Integer giftIntegration;

    @ApiModelProperty(value = "商品赠送成长值")
    @TableField("gift_growth")
    private Integer giftGrowth;

    @ApiModelProperty(value = "商品销售属性:[{\"key\":\"颜色\",\"value\":\"颜色\"},{\"key\":\"容量\",\"value\":\"4G\"}]")
    @TableField("product_attr")
    private String productAttr;


}

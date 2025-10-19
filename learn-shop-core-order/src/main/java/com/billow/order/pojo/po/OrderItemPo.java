package com.billow.order.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Table("oms_order_item")
@Schema(title = "OrderItemPo对象", description="")
public class OrderItemPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "订单id")
    @Column("order_id")
    private Long orderId;

    @Schema(title = "订单编号")
    @Column("order_sn")
    private String orderSn;

    @Schema(title = "商品id")
    @Column("product_id")
    private Long productId;

    @Schema(title = "商品图片")
    @Column("product_pic")
    private String productPic;

    @Schema(title = "商品名称")
    @Column("product_name")
    private String productName;

    @Schema(title = "商品品牌")
    @Column("product_brand")
    private String productBrand;

    @Schema(title = "商品条码")
    @Column("product_sn")
    private String productSn;

    @Schema(title = "销售价格")
    @Column("product_price")
    private BigDecimal productPrice;

    @Schema(title = "购买数量")
    @Column("product_quantity")
    private Integer productQuantity;

    @Schema(title = "商品sku编号")
    @Column("product_sku_id")
    private Long productSkuId;

    @Schema(title = "商品sku条码")
    @Column("product_sku_code")
    private String productSkuCode;

    @Schema(title = "商品分类id")
    @Column("product_category_id")
    private Long productCategoryId;

    @Schema(title = "商品的销售属性1")
    @Column("sp1")
    private String sp1;

    @Schema(title = "商品的销售属性2")
    @Column("sp2")
    private String sp2;

    @Schema(title = "商品的销售属性3")
    @Column("sp3")
    private String sp3;

    @Schema(title = "商品促销名称")
    @Column("promotion_name")
    private String promotionName;

    @Schema(title = "商品促销分解金额")
    @Column("promotion_amount")
    private BigDecimal promotionAmount;

    @Schema(title = "优惠券优惠分解金额")
    @Column("coupon_amount")
    private BigDecimal couponAmount;

    @Schema(title = "积分优惠分解金额")
    @Column("integration_amount")
    private BigDecimal integrationAmount;

    @Schema(title = "该商品经过优惠后的分解金额")
    @Column("real_amount")
    private BigDecimal realAmount;

    @Schema(title = "商品赠送积分")
    @Column("gift_integration")
    private Integer giftIntegration;

    @Schema(title = "商品赠送成长值")
    @Column("gift_growth")
    private Integer giftGrowth;

    @Schema(title = "商品销售属性:[{\"key\":\"颜色\",\"value\":\"颜色\"},{\"key\":\"容量\",\"value\":\"4G\"}]")
    @Column("product_attr")
    private String productAttr;


}

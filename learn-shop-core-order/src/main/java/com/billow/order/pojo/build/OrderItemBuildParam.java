package com.billow.order.pojo.build;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@Data
@Accessors(chain = true)
public class OrderItemBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "订单id")
    private Long orderId;

    @Schema(title = "订单编号")
    private String orderSn;

    @Schema(title = "商品id")
    private Long productId;

    @Schema(title = "商品图片")
    private String productPic;

    @Schema(title = "商品名称")
    private String productName;

    @Schema(title = "商品品牌")
    private String productBrand;

    @Schema(title = "商品条码")
    private String productSn;

    @Schema(title = "销售价格")
    private BigDecimal productPrice;

    @Schema(title = "购买数量")
    private Integer productQuantity;

    @Schema(title = "商品sku编号")
    private Long productSkuId;

    @Schema(title = "商品sku条码")
    private String productSkuCode;

    @Schema(title = "商品分类id")
    private Long productCategoryId;

    @Schema(title = "商品的销售属性1")
    private String sp1;

    @Schema(title = "商品的销售属性2")
    private String sp2;

    @Schema(title = "商品的销售属性3")
    private String sp3;

    @Schema(title = "商品促销名称")
    private String promotionName;

    @Schema(title = "商品促销分解金额")
    private BigDecimal promotionAmount;

    @Schema(title = "优惠券优惠分解金额")
    private BigDecimal couponAmount;

    @Schema(title = "积分优惠分解金额")
    private BigDecimal integrationAmount;

    @Schema(title = "该商品经过优惠后的分解金额")
    private BigDecimal realAmount;

    @Schema(title = "商品赠送积分")
    private Integer giftIntegration;

    @Schema(title = "商品赠送成长值")
    private Integer giftGrowth;

    @Schema(title = "商品销售属性:[{\"key\":\"颜色\",\"value\":\"颜色\"},{\"key\":\"容量\",\"value\":\"4G\"}]")
    private String productAttr;


}

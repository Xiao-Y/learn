package com.billow.order.pojo.vo;

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
 * @version v1.0
 * @since 2021-08-23
 */
@Data
@Accessors(chain = true)
public class CartItemVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "商品的id")
    private Long productId;

    @Schema(title = "商品sku的id")
    private Long productSkuId;

    @Schema(title = "会员id")
    private Long memberId;

    @Schema(title = "购买数量")
    private Integer quantity;

    @Schema(title = "添加到购物车的价格")
    private BigDecimal price;

    @Schema(title = "销售属性1")
    private String sp1;

    @Schema(title = "销售属性2")
    private String sp2;

    @Schema(title = "销售属性3")
    private String sp3;

    @Schema(title = "商品主图")
    private String productPic;

    @Schema(title = "商品名称")
    private String productName;

    @Schema(title = "商品品牌")
    private String productBrand;

    @Schema(title = "商品的条码")
    private String productSn;

    @Schema(title = "商品副标题（卖点）")
    private String productSubTitle;

    @Schema(title = "商品sku条码")
    private String productSkuCode;

    @Schema(title = "会员昵称")
    private String memberNickname;

    @Schema(title = "是否删除")
    private Integer deleteStatus;

    @Schema(title = "商品的分类")
    private Long productCategoryId;

    @Schema(title = "商品销售属性:[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"4G\"}]")
    private String productAttr;


}

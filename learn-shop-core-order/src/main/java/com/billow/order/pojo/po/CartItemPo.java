package com.billow.order.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("oms_cart_item")
@Schema(title = "CartItemPo对象", description="")
public class CartItemPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "商品的id")
    @TableField("product_id")
    private Long productId;

    @Schema(title = "商品sku的id")
    @TableField("product_sku_id")
    private Long productSkuId;

    @Schema(title = "会员id")
    @TableField("member_id")
    private Long memberId;

    @Schema(title = "购买数量")
    @TableField("quantity")
    private Integer quantity;

    @Schema(title = "添加到购物车的价格")
    @TableField("price")
    private BigDecimal price;

    @Schema(title = "销售属性1")
    @TableField("sp1")
    private String sp1;

    @Schema(title = "销售属性2")
    @TableField("sp2")
    private String sp2;

    @Schema(title = "销售属性3")
    @TableField("sp3")
    private String sp3;

    @Schema(title = "商品主图")
    @TableField("product_pic")
    private String productPic;

    @Schema(title = "商品名称")
    @TableField("product_name")
    private String productName;

    @Schema(title = "商品品牌")
    @TableField("product_brand")
    private String productBrand;

    @Schema(title = "商品的条码")
    @TableField("product_sn")
    private String productSn;

    @Schema(title = "商品副标题（卖点）")
    @TableField("product_sub_title")
    private String productSubTitle;

    @Schema(title = "商品sku条码")
    @TableField("product_sku_code")
    private String productSkuCode;

    @Schema(title = "会员昵称")
    @TableField("member_nickname")
    private String memberNickname;

    @Schema(title = "是否删除")
    @TableField("delete_status")
    private Integer deleteStatus;

    @Schema(title = "商品的分类")
    @TableField("product_category_id")
    private Long productCategoryId;

    @Schema(title = "商品销售属性:[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"4G\"}]")
    @TableField("product_attr")
    private String productAttr;


}

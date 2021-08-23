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
@TableName("oms_cart_item")
@ApiModel(value="CartItemPo对象", description="")
public class CartItemPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品的id")
    @TableField("product_id")
    private Long productId;

    @ApiModelProperty(value = "商品sku的id")
    @TableField("product_sku_id")
    private Long productSkuId;

    @ApiModelProperty(value = "会员id")
    @TableField("member_id")
    private Long memberId;

    @ApiModelProperty(value = "购买数量")
    @TableField("quantity")
    private Integer quantity;

    @ApiModelProperty(value = "添加到购物车的价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "销售属性1")
    @TableField("sp1")
    private String sp1;

    @ApiModelProperty(value = "销售属性2")
    @TableField("sp2")
    private String sp2;

    @ApiModelProperty(value = "销售属性3")
    @TableField("sp3")
    private String sp3;

    @ApiModelProperty(value = "商品主图")
    @TableField("product_pic")
    private String productPic;

    @ApiModelProperty(value = "商品名称")
    @TableField("product_name")
    private String productName;

    @ApiModelProperty(value = "商品品牌")
    @TableField("product_brand")
    private String productBrand;

    @ApiModelProperty(value = "商品的条码")
    @TableField("product_sn")
    private String productSn;

    @ApiModelProperty(value = "商品副标题（卖点）")
    @TableField("product_sub_title")
    private String productSubTitle;

    @ApiModelProperty(value = "商品sku条码")
    @TableField("product_sku_code")
    private String productSkuCode;

    @ApiModelProperty(value = "会员昵称")
    @TableField("member_nickname")
    private String memberNickname;

    @ApiModelProperty(value = "是否删除")
    @TableField("delete_status")
    private Integer deleteStatus;

    @ApiModelProperty(value = "商品的分类")
    @TableField("product_category_id")
    private Long productCategoryId;

    @ApiModelProperty(value = "商品销售属性:[{\"key\":\"颜色\",\"value\":\"银色\"},{\"key\":\"容量\",\"value\":\"4G\"}]")
    @TableField("product_attr")
    private String productAttr;


}

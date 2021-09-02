package com.billow.product.pojo.po;

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
 * spu表（Standard Product Unit, 标准产品单元）
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("pms_goods_spu")
@ApiModel(value="GoodsSpuPo对象", description="spu表（Standard Product Unit, 标准产品单元）")
public class GoodsSpuPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号，唯一")
    @TableField("spu_no")
    private String spuNo;

    @ApiModelProperty(value = "品牌属性分类id")
    @TableField("product_attribute_category_id")
    private Long productAttributeCategoryId;

    @ApiModelProperty(value = "品牌分类id")
    @TableField("category_id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌id")
    @TableField("brand_id")
    private Long brandId;

    @ApiModelProperty(value = "商品名称")
    @TableField("goods_name")
    private String goodsName;

    @ApiModelProperty(value = "关键字")
    @TableField("keywords")
    private String keywords;

    @ApiModelProperty(value = "副标题")
    @TableField("sub_title")
    private String subTitle;

    @ApiModelProperty(value = "详情标题")
    @TableField("detail_title")
    private String detailTitle;

    @ApiModelProperty(value = "图片")
    @TableField("pic")
    private String pic;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    @TableField("publish_status")
    private Integer publishStatus;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    @TableField("new_status")
    private Integer newStatus;

    @ApiModelProperty(value = "推荐状态；0->不推荐；1->推荐")
    @TableField("recommand_status")
    private Integer recommandStatus;

    @ApiModelProperty(value = "是否为预告商品：0->不是；1->是")
    @TableField("preview_status")
    private Integer previewStatus;

    @ApiModelProperty(value = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    @TableField("service_ids")
    private String serviceIds;

    @ApiModelProperty(value = "审核状态：0->未审核；1->审核通过")
    @TableField("verify_status")
    private Integer verifyStatus;

    @ApiModelProperty(value = "价格")
    @TableField("price")
    private BigDecimal price;

    @ApiModelProperty(value = "最低售价")
    @TableField("low_price")
    private Integer lowPrice;

    @ApiModelProperty(value = "销量")
    @TableField("sale")
    private Integer sale;

    @ApiModelProperty(value = "总库存量")
    @TableField("stock")
    private Long stock;

    @ApiModelProperty(value = "库存预警值")
    @TableField("low_stock")
    private Integer lowStock;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    @TableField("album_pics")
    private String albumPics;

    @ApiModelProperty(value = "商品描述")
    @TableField("description")
    private String description;

    @ApiModelProperty(value = "详情描述")
    @TableField("detail_desc")
    private String detailDesc;

    @ApiModelProperty(value = "产品详情网页内容")
    @TableField("detail_html")
    private String detailHtml;

    @ApiModelProperty(value = "移动端网页详情")
    @TableField("detail_mobile_html")
    private String detailMobileHtml;

    @ApiModelProperty(value = "运费模版id")
    @TableField("feight_template_id")
    private Long feightTemplateId;

    @ApiModelProperty(value = "商品排序")
    @TableField("spu_sort")
    private Long spuSort;

    @ApiModelProperty(value = "备注")
    @TableField("note")
    private String note;


}

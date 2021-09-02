package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSpuPo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * spu表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSpuVo extends GoodsSpuPo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品编号，唯一")
    private String spuNo;

    @ApiModelProperty(value = "品牌属性分类id")
    private Long productAttributeCategoryId;

    @ApiModelProperty(value = "品牌分类id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "关键字")
    private String keywords;

    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "详情标题")
    private String detailTitle;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    private Integer publishStatus;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    private Integer newStatus;

    @ApiModelProperty(value = "推荐状态；0->不推荐；1->推荐")
    private Integer recommandStatus;

    @ApiModelProperty(value = "是否为预告商品：0->不是；1->是")
    private Integer previewStatus;

    @ApiModelProperty(value = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    private String serviceIds;

    @ApiModelProperty(value = "审核状态：0->未审核；1->审核通过")
    private Integer verifyStatus;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "最低售价")
    private Integer lowPrice;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "总库存量")
    private Long stock;

    @ApiModelProperty(value = "库存预警值")
    private Integer lowStock;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    private String albumPics;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "详情描述")
    private String detailDesc;

    @ApiModelProperty(value = "产品详情网页内容")
    private String detailHtml;

    @ApiModelProperty(value = "移动端网页详情")
    private String detailMobileHtml;

    @ApiModelProperty(value = "运费模版id")
    private Long feightTemplateId;

    @ApiModelProperty(value = "商品排序")
    private Long spuSort;

    @ApiModelProperty(value = "备注")
    private String note;

    private List<Long> specKeys;
    private List<Long> specValues;
}

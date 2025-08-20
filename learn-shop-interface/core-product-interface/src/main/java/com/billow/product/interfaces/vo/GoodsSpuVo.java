package com.billow.product.interfaces.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
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
public class GoodsSpuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键id
    private Long id;

    @Schema(title = "商品编号，唯一")
    private String spuNo;

    @Schema(title = "品牌属性分类id")
    private Long productAttributeCategoryId;

    @Schema(title = "品牌分类id")
    private Long categoryId;

    @Schema(title = "品牌id")
    private Long brandId;

    @Schema(title = "商品名称")
    private String goodsName;

    @Schema(title = "关键字")
    private String keywords;

    @Schema(title = "副标题")
    private String subTitle;

    @Schema(title = "详情标题")
    private String detailTitle;

    @Schema(title = "图片")
    private String pic;

    @Schema(title = "上架状态：0->下架；1->上架")
    private Integer publishStatus;

    @Schema(title = "新品状态:0->不是新品；1->新品")
    private Integer newStatus;

    @Schema(title = "推荐状态；0->不推荐；1->推荐")
    private Integer recommandStatus;

    @Schema(title = "是否为预告商品：0->不是；1->是")
    private Integer previewStatus;

    @Schema(title = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    private String serviceIds;

    @Schema(title = "审核状态：0->未审核；1->审核通过")
    private Integer verifyStatus;

    @Schema(title = "价格")
    private Integer price;

    @Schema(title = "最低售价")
    private Integer lowPrice;

    @Schema(title = "销量")
    private Integer sale;

    @Schema(title = "总库存量")
    private Long stock;

    @Schema(title = "库存预警值")
    private Integer lowStock;

    @Schema(title = "画册图片，连产品图片限制为5张，以逗号分割")
    private String albumPics;

    @Schema(title = "商品描述")
    private String description;

    @Schema(title = "详情描述")
    private String detailDesc;

    @Schema(title = "产品详情网页内容")
    private String detailHtml;

    @Schema(title = "移动端网页详情")
    private String detailMobileHtml;

    @Schema(title = "运费模版id")
    private Long feightTemplateId;

    @Schema(title = "商品排序")
    private Long spuSort;

    @Schema(title = "备注")
    private String note;

    private List<Long> specKeys;
    private List<Long> specValues;
}

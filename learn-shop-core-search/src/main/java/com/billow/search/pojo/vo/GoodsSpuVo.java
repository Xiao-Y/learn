package com.billow.search.pojo.vo;


import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

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

    @ApiModelProperty(value = "GoodsSpu表主键")
    @JSONField(name = "id")
    private Long spuId;

    @ApiModelProperty(value = "商品编号，唯一")
    @JSONField(name = "spu_no")
    private String spuNo;

    @ApiModelProperty(value = "品牌属性分类id")
    @JSONField(name = "product_attribute_category_id")
    private Long productAttributeCategoryId;

    @ApiModelProperty(value = "品牌分类id")
    @JSONField(name = "category_id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌id")
    @JSONField(name = "brand_id")
    private Long brandId;

    @ApiModelProperty(value = "商品名称")
    @JSONField(name = "goods_name")
    private String goodsName;

    @ApiModelProperty(value = "关键字")
    private String keywords;

    @ApiModelProperty(value = "副标题")
    @JSONField(name = "sub_title")
    private String subTitle;

    @ApiModelProperty(value = "详情标题")
    @JSONField(name = "detail_title")
    private String detailTitle;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "上架状态：0->下架；1->上架")
    @JSONField(name = "publish_status")
    private Integer publishStatus;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    @JSONField(name = "new_status")
    private Integer newStatus;

    @ApiModelProperty(value = "推荐状态；0->不推荐；1->推荐")
    @JSONField(name = "recommand_status")
    private Integer recommandStatus;

    @ApiModelProperty(value = "是否为预告商品：0->不是；1->是")
    @JSONField(name = "preview_status")
    private Integer previewStatus;

    @ApiModelProperty(value = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    @JSONField(name = "service_ids")
    private String serviceIds;

    @ApiModelProperty(value = "审核状态：0->未审核；1->审核通过")
    @JSONField(name = "verify_status")
    private Integer verifyStatus;

    @ApiModelProperty(value = "价格")
    private Integer price;

    @ApiModelProperty(value = "最低售价")
    @JSONField(name = "low_price")
    private Integer lowPrice;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "总库存量")
    private Long stock;

    @ApiModelProperty(value = "库存预警值")
    @JSONField(name = "low_stock")
    private Integer lowStock;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    @JSONField(name = "album_pics")
    private String albumPics;

    @ApiModelProperty(value = "商品描述")
    private String description;

    @ApiModelProperty(value = "详情描述")
    @JSONField(name = "detail_desc")
    private String detailDesc;

    @ApiModelProperty(value = "产品详情网页内容")
    @JSONField(name = "detail_html")
    private String detailHtml;

    @ApiModelProperty(value = "移动端网页详情")
    @JSONField(name = "detail_mobile_html")
    private String detailMobileHtml;

    @ApiModelProperty(value = "运费模版id")
    @JSONField(name = "feight_template_id")
    private Long feightTemplateId;

    @ApiModelProperty(value = "商品排序")
    @JSONField(name = "spu_sort")
    private Long spuSort;

    @ApiModelProperty(value = "备注")
    private String note;

    // 创建人
    @JSONField(name = "creator_code")
    private String creatorCode;

    // 创建人
    @JSONField(name = "updater_code")
    private String updaterCode;

    // 创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(name = "create_time")
    private Date createTime;

    // 更新时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @JSONField(name = "update_time")
    private Date updateTime;

    // 有效标志
    @JSONField(name = "valid_ind")
    private Boolean validInd;
}

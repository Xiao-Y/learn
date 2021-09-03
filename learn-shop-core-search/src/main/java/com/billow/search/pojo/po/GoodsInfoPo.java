package com.billow.search.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.billow.search.common.cons.AnalyzerConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.DateFormat;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.Date;

/**
 * 商品信息
 *
 * @author liuyongtao
 * @since 2021-2-7 17:23
 */
@Data
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "goods_info")
public class GoodsInfoPo {

    // GoodsSpuPo
    @ApiModelProperty(value = "spu_id")
    @Id
    @JSONField(name = "id")
    private Long spuId;

    // GoodsCategoryPo
    @ApiModelProperty(value = "品牌分类id")
    @Field(type = FieldType.Keyword)
    @JSONField(name = "category_id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌分类名称")
    @Field(type = FieldType.Keyword)
    private String categoryName;

    // GoodsBrandPo
    @ApiModelProperty(value = "品牌id")
    @Field(type = FieldType.Keyword)
    @JSONField(name = "brand_id")
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    @Field(type = FieldType.Keyword)
    private String brandName;

    // GoodsSpuPo
    @ApiModelProperty(value = "商品编号，唯一")
    @Field(type = FieldType.Keyword)
    @JSONField(name = "spu_no")
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    @JSONField(name = "goods_name")
    private String goodsName;

    @ApiModelProperty(value = "关键字")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String keywords;

    @ApiModelProperty(value = "副标题")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    @JSONField(name = "sub_title")
    private String subTitle;

    @ApiModelProperty(value = "详情标题")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    @JSONField(name = "detail_title")
    private String detailTitle;

    @ApiModelProperty(value = "图片")
    private String pic;

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
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    @JSONField(name = "service_ids")
    private String serviceIds;

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
    private Long lowStock;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    @JSONField(name = "album_pics")
    private String albumPics;

    @ApiModelProperty(value = "商品描述")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String description;

    @ApiModelProperty(value = "商品排序")
    @JSONField(name = "spu_sort")
    private Long spuSort;

    // 创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX")
    @JSONField(name = "create_time")
    private Date createTime;

    // 更新时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX")
    @JSONField(name = "update_time")
    private Date updateTime;
}

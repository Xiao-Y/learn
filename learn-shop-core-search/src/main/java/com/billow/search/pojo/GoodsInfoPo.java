package com.billow.search.pojo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.billow.search.constant.AnalyzerConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

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
    private Long spuId;

    // GoodsCategoryPo
    @ApiModelProperty(value = "品牌分类id")
    @Field(type = FieldType.Keyword)
    private Long categoryId;

    @ApiModelProperty(value = "品牌分类名称")
    @Field(type = FieldType.Keyword)
    private String categoryName;

    // GoodsBrandPo
    @ApiModelProperty(value = "品牌id")
    @Field(type = FieldType.Keyword)
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    @Field(type = FieldType.Keyword)
    private String brandName;

    // GoodsSpuPo
    @ApiModelProperty(value = "商品编号，唯一")
    @Field(type = FieldType.Keyword)
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String goodsName;

    @ApiModelProperty(value = "关键字")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String keywords;

    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    @ApiModelProperty(value = "副标题")
    private String subTitle;

    @ApiModelProperty(value = "详情标题")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String detailTitle;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    private Integer newStatus;

    @ApiModelProperty(value = "推荐状态；0->不推荐；1->推荐")
    private Integer recommandStatus;

    @ApiModelProperty(value = "是否为预告商品：0->不是；1->是")
    private Integer previewStatus;

    @ApiModelProperty(value = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String serviceIds;

    @ApiModelProperty(value = "价格")
    private Integer price;

    @ApiModelProperty(value = "最低售价")
    private Integer lowPrice;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "总库存量")
    private Long stock;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    private String albumPics;

    @ApiModelProperty(value = "商品描述")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String description;

    @ApiModelProperty(value = "商品排序")
    private Long spuSort;

}

package com.billow.search.pojo;

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
@Document(indexName = "spu_info")
public class SpuInfo {

    @ApiModelProperty(value = "spu_id")
    @Id
    private Long spuId;

    // GoodsBrandPo
    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    @Field(type = FieldType.Keyword)
    private String brandName;

    // GoodsCategoryPo
    @ApiModelProperty(value = "分类id")
    private Long categoryId;

    @ApiModelProperty(value = "分类名称")
    @Field(type = FieldType.Keyword)
    private String categoryName;

    // GoodsSpuPo
    @ApiModelProperty(value = "商品编号，唯一")
    @Field(type = FieldType.Keyword)
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    private String goodsName;

    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER)
    @ApiModelProperty(value = "子标题")
    private String subTitle;

    @ApiModelProperty(value = "最低售价")
    private Integer lowPrice;

    @ApiModelProperty(value = "总库存量")
    private Long stock;

}

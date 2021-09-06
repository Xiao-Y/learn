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

import java.time.LocalDateTime;
import java.util.Objects;

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
    @Field(type = FieldType.Long)
    @JSONField(name = "category_id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌分类名称")
    @Field(type = FieldType.Keyword)
    private String categoryName;

    // GoodsBrandPo
    @ApiModelProperty(value = "品牌id")
    @Field(type = FieldType.Long)
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
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER, normalizer = AnalyzerConstant.NORMALIZER_LOWERCASE)
    @JSONField(name = "goods_name")
    private String goodsName;

    @ApiModelProperty(value = "关键字")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER, normalizer = AnalyzerConstant.NORMALIZER_LOWERCASE)
    private String keywords;

    @ApiModelProperty(value = "副标题")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER, normalizer = AnalyzerConstant.NORMALIZER_LOWERCASE)
    @JSONField(name = "sub_title")
    private String subTitle;

    @ApiModelProperty(value = "详情标题")
    @Field(type = FieldType.Text, analyzer = AnalyzerConstant.ANALYZER, normalizer = AnalyzerConstant.NORMALIZER_LOWERCASE)
    @JSONField(name = "detail_title")
    private String detailTitle;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    @Field(type = FieldType.Integer)
    @JSONField(name = "new_status")
    private Integer newStatus;

    @ApiModelProperty(value = "推荐状态；0->不推荐；1->推荐")
    @Field(type = FieldType.Integer)
    @JSONField(name = "recommand_status")
    private Integer recommandStatus;

    @ApiModelProperty(value = "是否为预告商品：0->不是；1->是")
    @Field(type = FieldType.Integer)
    @JSONField(name = "preview_status")
    private Integer previewStatus;

    @ApiModelProperty(value = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    @JSONField(name = "service_ids")
    private String serviceIds;

    @ApiModelProperty(value = "价格")
    @Field(type = FieldType.Integer)
    private Integer price;

    @ApiModelProperty(value = "最低售价")
    @Field(type = FieldType.Integer)
    @JSONField(name = "low_price")
    private Integer lowPrice;

    @ApiModelProperty(value = "销量")
    @Field(type = FieldType.Integer)
    private Integer sale;

    @ApiModelProperty(value = "总库存量")
    @Field(type = FieldType.Long)
    private Long stock;

    @ApiModelProperty(value = "库存预警值")
    @Field(type = FieldType.Long)
    @JSONField(name = "low_stock")
    private Long lowStock;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    @JSONField(name = "album_pics")
    private String albumPics;

    @ApiModelProperty(value = "商品排序")
    @Field(type = FieldType.Long)
    @JSONField(name = "spu_sort")
    private Long spuSort;

    // 创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd HH:mm:ss")
//    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    @JSONField(name = "create_time")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
//    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd'T'HH:mm:ss.SSSX")
    @Field(type = FieldType.Date, format = DateFormat.custom, pattern = "uuuu-MM-dd HH:mm:ss")
//    @Field(type = FieldType.Date, format = DateFormat.date_hour_minute_second)
    @JSONField(name = "update_time")
    private LocalDateTime updateTime;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GoodsInfoPo that = (GoodsInfoPo) o;
        return Objects.equals(spuId, that.spuId) && Objects.equals(categoryId, that.categoryId)
                && Objects.equals(brandId, that.brandId) && Objects.equals(spuNo, that.spuNo)
                && Objects.equals(goodsName, that.goodsName) && Objects.equals(keywords, that.keywords)
                && Objects.equals(subTitle, that.subTitle) && Objects.equals(detailTitle, that.detailTitle)
                && Objects.equals(pic, that.pic) && Objects.equals(newStatus, that.newStatus)
                && Objects.equals(recommandStatus, that.recommandStatus) && Objects.equals(previewStatus, that.previewStatus)
                && Objects.equals(serviceIds, that.serviceIds) && Objects.equals(price, that.price)
                && Objects.equals(lowPrice, that.lowPrice) && Objects.equals(sale, that.sale)
                && Objects.equals(stock, that.stock) && Objects.equals(lowStock, that.lowStock)
                && Objects.equals(albumPics, that.albumPics) && Objects.equals(spuSort, that.spuSort);
    }

    @Override
    public int hashCode() {
        return Objects.hash(spuId, categoryId, brandId, spuNo, goodsName, keywords, subTitle, detailTitle, pic,
                newStatus, recommandStatus, previewStatus, serviceIds, price, lowPrice, sale, stock, lowStock, albumPics, spuSort);
    }
}

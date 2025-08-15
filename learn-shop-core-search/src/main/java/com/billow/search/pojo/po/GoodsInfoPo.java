package com.billow.search.pojo.po;

import com.alibaba.fastjson.annotation.JSONField;
import com.billow.search.common.cons.AnalyzerConstant;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.dromara.easyes.annotation.HighLight;
import org.dromara.easyes.annotation.IndexField;
import org.dromara.easyes.annotation.IndexId;
import org.dromara.easyes.annotation.IndexName;
import org.dromara.easyes.annotation.rely.FieldType;
import org.dromara.easyes.annotation.rely.IdType;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 商品信息
 *
 * @author liuyongtao
 * @since 2021-2-7 17:23
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@IndexName(value = GoodsInfoPo.ES_INDEX_GOODS_INFO)
public class GoodsInfoPo {

    public final static String ES_INDEX_GOODS_INFO = "goods_info";

    // GoodsSpuPo
    @ApiModelProperty(value = "spu_id")
    @IndexId(type = IdType.CUSTOMIZE)
    @JSONField(name = "id")
    private Long spuId;

    // GoodsCategoryPo
    @ApiModelProperty(value = "品牌分类id")
    @IndexField(fieldType = FieldType.LONG)
    @JSONField(name = "category_id")
    private Long categoryId;

    @ApiModelProperty(value = "品牌分类名称")
    @HighLight(mappingField = "categoryNameHighlight", preTag = "<font color='red'>", postTag = "</font>")
    @IndexField(fieldType = FieldType.KEYWORD)
    private String categoryName;
    private String categoryNameHighlight;

    // GoodsBrandPo
    @ApiModelProperty(value = "品牌id")
    @IndexField(fieldType = FieldType.LONG)
    @JSONField(name = "brand_id")
    private Long brandId;

    @ApiModelProperty(value = "品牌名称")
    @HighLight(mappingField = "brandNameHighlight", preTag = "<font color='red'>", postTag = "</font>")
    @IndexField(fieldType = FieldType.KEYWORD)
    private String brandName;
    private String brandNameHighlight;

    // GoodsSpuPo
    @ApiModelProperty(value = "商品编号，唯一")
    @IndexField(fieldType = FieldType.KEYWORD)
    @JSONField(name = "spu_no")
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    @HighLight(mappingField = "goodsNameHighlight", preTag = "<font color='red'>", postTag = "</font>")
    @IndexField(fieldType = FieldType.TEXT, analyzer = AnalyzerConstant.ANALYZER, ignoreCase = true)
    @JSONField(name = "goods_name")
    private String goodsName;
    private String goodsNameHighlight;

    @ApiModelProperty(value = "关键字")
    @HighLight(mappingField = "keywordsHighlight", preTag = "<font color='red'>", postTag = "</font>")
    @IndexField(fieldType = FieldType.TEXT, analyzer = AnalyzerConstant.ANALYZER, ignoreCase = true)
    private String keywords;
    private String keywordsHighlight;

    @ApiModelProperty(value = "副标题")
    @HighLight(mappingField = "subTitleHighlight", preTag = "<font color='red'>", postTag = "</font>")
    @IndexField(fieldType = FieldType.TEXT, analyzer = AnalyzerConstant.ANALYZER, ignoreCase = true)
    @JSONField(name = "sub_title")
    private String subTitle;
    private String subTitleHighlight;

    @ApiModelProperty(value = "详情标题")
    @HighLight(mappingField = "detailTitleHighlight", preTag = "<font color='red'>", postTag = "</font>")
    @IndexField(fieldType = FieldType.TEXT, analyzer = AnalyzerConstant.ANALYZER, ignoreCase = true)
    @JSONField(name = "detail_title")
    private String detailTitle;
    private String detailTitleHighlight;

    @ApiModelProperty(value = "图片")
    private String pic;

    @ApiModelProperty(value = "新品状态:0->不是新品；1->新品")
    @IndexField(fieldType = FieldType.INTEGER)
    @JSONField(name = "new_status")
    private Integer newStatus;

    @ApiModelProperty(value = "推荐状态；0->不推荐；1->推荐")
    @IndexField(fieldType = FieldType.INTEGER)
    @JSONField(name = "recommand_status")
    private Integer recommandStatus;

    @ApiModelProperty(value = "是否为预告商品：0->不是；1->是")
    @IndexField(fieldType = FieldType.INTEGER)
    @JSONField(name = "preview_status")
    private Integer previewStatus;

    @ApiModelProperty(value = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    @JSONField(name = "service_ids")
    private String serviceIds;

    @ApiModelProperty(value = "价格")
    @IndexField(fieldType = FieldType.INTEGER)
    private Integer price;

    @ApiModelProperty(value = "最低售价")
    @IndexField(fieldType = FieldType.INTEGER)
    @JSONField(name = "low_price")
    private Integer lowPrice;

    @ApiModelProperty(value = "销量")
    @IndexField(fieldType = FieldType.INTEGER)
    private Integer sale;

    @ApiModelProperty(value = "总库存量")
    @IndexField(fieldType = FieldType.LONG)
    private Long stock;

    @ApiModelProperty(value = "库存预警值")
    @IndexField(fieldType = FieldType.LONG)
    @JSONField(name = "low_stock")
    private Long lowStock;

    @ApiModelProperty(value = "画册图片，连产品图片限制为5张，以逗号分割")
    @JSONField(name = "album_pics")
    private String albumPics;

    @ApiModelProperty(value = "商品排序")
    @IndexField(fieldType = FieldType.LONG)
    @JSONField(name = "spu_sort")
    private Long spuSort;

    // 创建时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss")
    @JSONField(name = "create_time")
    private LocalDateTime createTime;

    // 更新时间
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @IndexField(fieldType = FieldType.DATE, dateFormat = "yyyy-MM-dd HH:mm:ss")
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

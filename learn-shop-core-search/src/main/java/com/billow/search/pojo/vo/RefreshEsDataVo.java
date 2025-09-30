package com.billow.search.pojo.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品信息
 *
 * @author liuyongtao
 * @since 2021-2-7 17:23
 */
@Data
public class RefreshEsDataVo {

    // GoodsSpuPo
    @Schema(title = "spu_id")
    private Long spuId;

    // GoodsCategoryPo
    @Schema(title = "品牌分类id")
    private Long categoryId;

    @Schema(title = "品牌分类名称")
    private String categoryName;

    // GoodsBrandPo
    @Schema(title = "品牌id")
    private Long brandId;

    @Schema(title = "品牌名称")
    private String brandName;

    // GoodsSpuPo
    @Schema(title = "商品编号，唯一")
    private String spuNo;

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

    @Schema(title = "新品状态:0->不是新品；1->新品")
    private Integer newStatus;

    @Schema(title = "推荐状态；0->不推荐；1->推荐")
    private Integer recommandStatus;

    @Schema(title = "是否为预告商品：0->不是；1->是")
    private Integer previewStatus;

    @Schema(title = "以逗号分割的产品服务：1->无忧退货；2->快速退款；3->免费包邮")
    private String serviceIds;

    @Schema(title = "价格")
    private Integer price;

    @Schema(title = "最低售价")
    private Integer lowPrice;

    @Schema(title = "销量")
    private Integer sale;

    @Schema(title = "总库存量")
    private Long stock;

    @Schema(title = "库存预警值")
    private Long lowStock;

    @Schema(title = "画册图片，连产品图片限制为5张，以逗号分割")
    private String albumPics;

    @Schema(title = "商品描述")
    private String description;

    @Schema(title = "商品排序")
    private Long spuSort;

    @Schema(title = "审核状态：0->未审核；1->审核通过")
    private Integer verifyStatus;

    @Schema(title = "上架状态：0->下架；1->上架")
    private Integer publishStatus;

    // 创建时间
    private Date createTime;

    // 更新时间
    private Date updateTime;
}

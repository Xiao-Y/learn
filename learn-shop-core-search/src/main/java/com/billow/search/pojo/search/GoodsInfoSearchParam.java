package com.billow.search.pojo.search;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 查询条件
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsInfoSearchParam implements Serializable {

    @Schema(title = "关键字：商品名称、品牌名称、品牌分类名称，多个时用空格分开")
    private String keyWorlds;

    @Schema(title = "商品编号，唯一")
    private String spuNo;

    @Schema(title = "品牌id")
    private Long brandId;

    @Schema(title = "品牌分类id")
    private Long categoryId;

    @Schema(title = "新品状态:0->不是新品；1->新品")
    private Integer newStatus;

    @Schema(title = "推荐状态；0->不推荐；1->推荐")
    private Integer recommandStatus;

    @Schema(title = "是否为预告商品：0->不是；1->是")
    private Integer previewStatus;

    @Schema(title = "价格范围，格式：12~15（大于等于12，小于等于15），~15（小于15），12~（大于12）")
    private String price;
}

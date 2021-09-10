package com.billow.search.interfaces.search;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "关键字：商品名称、品牌名称、品牌分类名称，多个时用空格分开")
    private String keyWorlds;

    @ApiModelProperty(value = "商品编号，唯一")
    private String spuNo;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "品牌分类id")
    private Long categoryId;

    @ApiModelProperty(value = "价格范围，格式：12~15（大于等于12，小于等于15），~15（小于15），12~（大于12）")
    private String price;
}

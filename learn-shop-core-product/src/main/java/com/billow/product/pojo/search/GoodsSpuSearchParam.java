package com.billow.product.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * spu表（Standard Product Unit, 标准产品单元） 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSpuSearchParam extends BasePage implements Serializable {

    @ApiModelProperty(value = "商品编号，唯一")
    private String spuNo;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "品牌id")
    private Long brandId;

    @ApiModelProperty(value = "品牌分类id")
    private Long categoryId;
}

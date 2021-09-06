package com.billow.product.pojo.build;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 商品操作记录表，用于记录商品操作记录

 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsOperateLogBuildParam implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "商品id")
    private Long spuId;

    @ApiModelProperty(value = "改变前价格")
    private BigDecimal priceOld;

    @ApiModelProperty(value = "改变后价格")
    private BigDecimal priceNew;

    @ApiModelProperty(value = "操作人")
    private String operateMan;


}

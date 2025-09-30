package com.billow.product.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
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
 * @version v1.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsOperateLogVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "商品id")
    private Long spuId;

    @Schema(title = "改变前价格")
    private BigDecimal priceOld;

    @Schema(title = "改变后价格")
    private BigDecimal priceNew;

    @Schema(title = "操作人")
    private String operateMan;


}

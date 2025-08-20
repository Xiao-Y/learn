package com.billow.order.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 秒杀订单mq
 *
 * @author liuyongtao
 * @since 2021-8-31 11:08
 */
@Data
public class OrderMqVo {

    @Schema(title = "订单类型：0->正常订单；1->秒杀订单")
    private Integer orderType;

    @Schema(title = "订单用户code")
    private String usercode;

    @Schema(title = "商品价格")
    private Long productId;

    @Schema(title = "skuid")
    private Long skuId;

    @Schema(title = "价格")
    private BigDecimal price;

    @Schema(title = "数量")
    private Integer count;
}

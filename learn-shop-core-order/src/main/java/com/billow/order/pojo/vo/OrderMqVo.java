package com.billow.order.pojo.vo;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(value = "订单类型：0->正常订单；1->秒杀订单")
    private Integer orderType;

    @ApiModelProperty(value = "订单用户code")
    private String usercode;

    @ApiModelProperty(value = "商品价格")
    private Long productId;

    @ApiModelProperty(value = "skuid")
    private Long skuId;

    @ApiModelProperty(value = "价格")
    private BigDecimal price;

    @ApiModelProperty(value = "数量")
    private Integer count;
}

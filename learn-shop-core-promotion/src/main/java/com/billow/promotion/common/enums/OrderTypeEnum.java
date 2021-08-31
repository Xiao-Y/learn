package com.billow.promotion.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 订单类型：0->正常订单；1->秒杀订单
 *
 * @author liuyongtao
 * @since 2021-8-31 14:28
 */
@Getter
@AllArgsConstructor
public enum OrderTypeEnum {

    COMMON(0, "正常订单"),
    SEC_KILL(1, "秒杀订单");

    private int type;
    private String desc;
}

package com.billow.product.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 新品状态:0->不是新品；1->新品
 *
 * @author liuyongtao
 * @since 2021-9-3 9:22
 */
@Getter
@AllArgsConstructor
public enum SpuNewStatusEnum {

    NO(0, "不是新品"),
    Yes(1, "新品");

    private int status;
    private String desc;
}

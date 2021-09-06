package com.billow.product.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 推荐状态；0->不推荐；1->推荐
 *
 * @author liuyongtao
 * @since 2021-9-3 9:22
 */
@Getter
@AllArgsConstructor
public enum SpuRecommandStatusEnum {

    NO(0, "不推荐"),
    Yes(1, "推荐");

    private int status;
    private String desc;
}

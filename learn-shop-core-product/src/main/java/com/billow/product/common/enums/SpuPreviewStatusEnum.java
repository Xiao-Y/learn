package com.billow.product.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 是否为预告商品：0->不是；1->是
 *
 * @author liuyongtao
 * @since 2021-9-3 9:22
 */
@Getter
@AllArgsConstructor
public enum SpuPreviewStatusEnum {

    NO(0, "不是"),
    Yes(1, "是");

    private int status;
    private String desc;
}

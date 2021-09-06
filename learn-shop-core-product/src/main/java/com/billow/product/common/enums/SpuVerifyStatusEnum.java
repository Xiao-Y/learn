package com.billow.product.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 审核状态：0->未审核；1->审核通过
 *
 * @author liuyongtao
 * @since 2021-9-3 9:22
 */
@Getter
@AllArgsConstructor
public enum SpuVerifyStatusEnum {

    NOT_APPROVED(0, "未审核"),
    APPROVED(1, "审核通过");

    private int status;
    private String desc;
}

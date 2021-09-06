package com.billow.product.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 限时购场次表状态
 *
 * @author liuyongtao
 * @since 2021-8-31 16:05
 */
@Getter
@AllArgsConstructor
public enum SeckillSessionStatusEnum {
    ON(0, "启用"),
    OFF(1, "禁用");

    private int status;
    private String desc;
}

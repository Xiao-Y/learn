package com.billow.product.common.enums;

import com.billow.excel.provider.impl.EnumDictProvider;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 限时购表状态
 *
 * @author liuyongtao
 * @since 2021-8-31 16:05
 */
@Getter
@AllArgsConstructor
public enum SeckillStatus2Enum implements EnumDictProvider.DictEnum {
    UP("0", "上架"),
    DOWN("1", "下架");

    private String value;
    private String label;
}

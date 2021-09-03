package com.billow.search.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 上架状态：0->下架；1->上架
 *
 * @author liuyongtao
 * @since 2021-9-3 9:22
 */
@Getter
@AllArgsConstructor
public enum SpuPublishStatusEnum {

    DOWN(0, "下架"),
    UP(1, "上架");

    private int status;
    private String desc;
}

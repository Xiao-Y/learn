package com.billow.product.interfaces.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * app 端 分类树
 *
 * @author liuyongtao
 * @since 2021-9-9 8:47
 */
@Data
public class GoodsCategoryTreeVo {

    @Schema(title = "分类id")
    private Long id;

    @Schema(title = "分类名称")
    private String categoryName;

    @Schema(title = "禁用选项")
    private Boolean validInd;
}

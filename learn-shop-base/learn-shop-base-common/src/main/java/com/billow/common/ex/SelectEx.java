package com.billow.common.ex;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 下拉列表组件模型
 *
 * @author liuyongtao
 * @create 2019-12-05 11:05
 */
@Data
public class SelectEx {

    @Schema(title = "名称id")
    private String id;

    @Schema(title = "显示的名称")
    private String fieldDisplay;

    @Schema(title = "显示名称的值")
    private String fieldValue;

    @Schema(title = "字段排序")
    private Long fieldOrder;
}
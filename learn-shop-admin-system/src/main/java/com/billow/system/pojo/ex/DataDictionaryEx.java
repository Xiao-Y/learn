package com.billow.system.pojo.ex;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

/**
 * @author liuyongtao
 * @create 2019-07-30 17:18
 */
@Data
public class DataDictionaryEx implements Serializable {

    @Schema(title = "字典 id")
    private Long id;

    @Schema(title = "显示的名称")
    private String fieldDisplay;

    @Schema(title = "显示名称的值")
    private Long fieldValue;

    public DataDictionaryEx(Long id, String fieldDisplay, Long fieldValue) {
        this.id = id;
        this.fieldDisplay = fieldDisplay;
        this.fieldValue = fieldValue;
    }
}

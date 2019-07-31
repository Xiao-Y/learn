package com.billow.system.pojo.ex;

import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-07-30 17:18
 */
@Data
public class DataDictionaryEx {
    private Long id;
    // 显示的名称
    private String fieldDisplay;
    // 显示名称的值
    private Long fieldValue;

    public DataDictionaryEx(Long id, String fieldDisplay, Long fieldValue) {
        this.id = id;
        this.fieldDisplay = fieldDisplay;
        this.fieldValue = fieldValue;
    }
}

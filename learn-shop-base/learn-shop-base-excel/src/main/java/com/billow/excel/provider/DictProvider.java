package com.billow.excel.provider;

import com.billow.excel.annotation.ExcelColumn;

import java.util.Map;

/**
 * 字典提供者接口
 */
public interface DictProvider {
    /**
     * 是否支持该字典类型
     */
    boolean support(String dictCode);

    /**
     * 获取字典数据
     */
    Map<String, String> getDictData(String dictCode);

    /**
     * 获取提供者类型
     */
    ExcelColumn.DictType getType();
}

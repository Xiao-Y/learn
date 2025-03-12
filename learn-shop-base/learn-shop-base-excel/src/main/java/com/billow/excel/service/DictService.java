package com.billow.excel.service;

import com.billow.excel.annotation.ExcelColumn;

import java.util.List;

/**
 * 字典服务接口
 */
public interface DictService {
    /**
     * 获取字典标签列表
     *
     * @param dictCode 字典编码
     * @return 标签列表
     */
    List<String> getDictLabels(String dictCode);

    /**
     * 获取字典值对应的标签
     *
     * @param dictCode 字典编码
     * @param value    字典值
     * @param dictType 字典类型
     * @return 标签
     */
    String getLabelByValue(String dictCode, String value, ExcelColumn.DictType dictType);

    /**
     * 获取字典标签对应的值
     *
     * @param dictCode 字典编码
     * @param label    标签
     * @param dictType 字典类型
     * @return 字典值
     */
    String getValueByLabel(String dictCode, String label, ExcelColumn.DictType dictType);

}
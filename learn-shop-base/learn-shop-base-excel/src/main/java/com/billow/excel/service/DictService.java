package com.billow.excel.service;

import com.billow.excel.annotation.ExcelDict;

import java.util.List;
import java.util.Map;

/**
 * 字典服务接口
 */
public interface DictService {
//    /**
//     * 获取字典标签列表
//     *
//     * @param dictCode 字典编码
//     * @return 标签列表
//     */
//    List<String> getDictLabels(String dictCode, ExcelDict.DictType dictType);

    /**
     * 获取字典值对应的标签（value-label）
     *
     * @author 千面
     */
    Map<String, String> getDictMapValue(String dictCode, ExcelDict.DictType dictType);

    /**
     * 获取字典值对应的标签（label-value）
     *
     * @author 千面
     */
    Map<String, String> getDictMapLabel(String dictCode, ExcelDict.DictType dictType);

//    /**
//     * 获取字典值对应的标签
//     *
//     * @param dictCode 字典编码
//     * @param value    字典值
//     * @param dictType 字典类型
//     * @return 标签
//     */
//    String getLabelByValue(String dictCode, String value, ExcelDict.DictType dictType);
//
//    /**
//     * 获取字典标签对应的值
//     *
//     * @param dictCode 字典编码
//     * @param label    标签
//     * @param dictType 字典类型
//     * @return 字典值
//     */
//    String getValueByLabel(String dictCode, String label, ExcelDict.DictType dictType);
//
//    /**
//     * 清除字典缓存
//     *
//     * @return 字典值
//     */
//    void clearCache();

}

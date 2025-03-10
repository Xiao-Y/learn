package com.billow.excel.service;

import java.util.List;
import java.util.Map;

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
     * @param value 字典值
     * @return 标签
     */
    String getLabelByValue(String dictCode, String value);

    /**
     * 获取字典标签对应的值
     *
     * @param dictCode 字典编码
     * @param label 标签
     * @return 字典值
     */
    String getValueByLabel(String dictCode, String label);

    /**
     * 获取字典所有键值对
     *
     * @param dictCode 字典编码
     * @return 键值对映射
     */
    Map<String, String> getDictMap(String dictCode);
} 
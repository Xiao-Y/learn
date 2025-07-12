package com.billow.excel.service.impl;

import com.billow.excel.annotation.ExcelDict;
import com.billow.excel.provider.DictProvider;
import com.billow.excel.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 复合字典服务实现
 * 支持多种数据源：枚举、数据库、缓存等
 */
@Slf4j
public class CompositeDictService implements DictService {


    /**
     * 字典提供者列表
     */
    private List<DictProvider> providers;

    @Autowired
    public void setProviders(List<DictProvider> providers) {
        // 按照 @Order 注解排序
        this.providers = new ArrayList<>(providers);
        this.providers.sort(Comparator.comparingInt(provider ->
                Optional.ofNullable(provider.getClass().getAnnotation(org.springframework.core.annotation.Order.class))
                        .map(org.springframework.core.annotation.Order::value)
                        .orElse(Integer.MAX_VALUE)));

        // 打印提供者加载顺序
        log.info("字典提供者加载顺序：");
        for (DictProvider provider : this.providers) {
            log.info("- {}", provider.getClass().getSimpleName());
        }
    }
//
//    /**
//     * 获取字典标签列表
//     */
//    @Override
//    public List<String> getDictLabels(String dictCode, ExcelDict.DictType dictType) {
//        return new ArrayList<>(getDictMap(dictCode, dictType).values());
//    }

    /**
     * 获取字典值对应的标签（value-label）
     */
    @Override
    public Map<String, String> getDictMapValue(String dictCode, ExcelDict.DictType dictType) {
        return getDictMap(dictCode, dictType);
    }

    /**
     * 获取字典标签对应的值（label-value）
     */
    @Override
    public Map<String, String> getDictMapLabel(String dictCode, ExcelDict.DictType dictType) {
        Map<String, String> dictMap = getDictMap(dictCode, dictType);
        return dictMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getValue, Map.Entry::getKey, (k1, k2) -> k1));
    }

    /**
     * 获取字典所有键值对
     */
    private Map<String, String> getDictMap(String dictCode, ExcelDict.DictType dictType) {
        // 获取指定类型的提供者
        DictProvider provider = providers.stream()
                .filter(p -> p.getType() == dictType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未找到字典提供者: " + dictType));

        // 从缓存获取
        Map<String, String> dictMap = new HashMap<>();
        // 从提供者获取数据
        if (provider.support(dictCode)) {
            dictMap = provider.getDictData(dictCode);
        }
        return dictMap;
    }
}
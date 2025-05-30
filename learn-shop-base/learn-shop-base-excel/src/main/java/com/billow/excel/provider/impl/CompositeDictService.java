package com.billow.excel.service.impl;

import com.billow.excel.annotation.ExcelColumn.DictType;
import com.billow.excel.provider.DictProvider;
import com.billow.excel.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

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

    /**
     * 字典缓存
     * key: dictCode
     * value: {dictValue: dictLabel}
     */
    private final Map<String, Map<String, String>> dictCache = new ConcurrentHashMap<>();

    /**
     * 缓存是空的
     *
     * @author 千面
     */
    private final List<String> dictCacheEmpty = new CopyOnWriteArrayList<>();

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

    /**
     * 获取字典标签列表
     */
    @Override
    public List<String> getDictLabels(String dictCode, DictType dictType) {
        return new ArrayList<>(getDictMap(dictCode, dictType).values());
    }

    /**
     * 获取字典值对应的标签
     */
    @Override
    public String getLabelByValue(String dictCode, String value, DictType dictType) {
        return getDictMap(dictCode, dictType).get(value);
    }

    /**
     * 获取字典标签对应的值
     */
    @Override
    public String getValueByLabel(String dictCode, String label, DictType dictType) {
        Map<String, String> dictMap = getDictMap(dictCode, dictType);
        return dictMap.entrySet().stream()
                .filter(entry -> entry.getValue().equals(label))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }

    /**
     * 获取字典所有键值对
     */
    public Map<String, String> getDictMap(String dictCode, DictType dictType) {
        // 如果是自动模式，使用原有逻辑
        if (dictType == DictType.NULL) {
            return Collections.emptyMap();
        }

        // 获取指定类型的提供者
        DictProvider provider = providers.stream()
                .filter(p -> p.getType() == dictType)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未找到字典提供者: " + dictType));

        // 从缓存获取
        String cacheKey = dictCode + ":" + dictType;
        Map<String, String> dictMap = dictCache.get(cacheKey);
        if (dictMap != null) {
            return dictMap;
        }
        // 说明已经查询过，但是没有数据
        if (dictCacheEmpty.contains(cacheKey)) {
            return Collections.emptyMap();
        }

        // 从提供者获取数据
        if (provider.support(dictCode)) {
            dictMap = provider.getDictData(dictCode);
            if (dictMap != null && !dictMap.isEmpty()) {
                dictCache.put(cacheKey, dictMap);
                return dictMap;
            }
        }
        // 记录缓存为空的
        dictCacheEmpty.add(cacheKey);
        return Collections.emptyMap();
    }

    /**
     * 清除字典缓存
     */
    @Override
    public void clearCache() {
        dictCache.clear();
        dictCacheEmpty.clear();
    }

    /**
     * 清除指定字典的缓存
     */
    public void clearCache(String dictCode) {
        // 清除自动模式的缓存
        dictCache.remove(dictCode);
        dictCacheEmpty.remove(dictCode);
    }
} 

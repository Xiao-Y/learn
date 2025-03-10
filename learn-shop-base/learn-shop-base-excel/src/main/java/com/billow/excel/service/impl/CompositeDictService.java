package com.billow.excel.service.impl;

import com.billow.excel.annotation.ExcelColumn.DictType;
import com.billow.excel.service.DictService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 复合字典服务实现
 * 支持多种数据源：枚举、数据库、缓存等
 */
@Slf4j
@Primary
@Service
public class CompositeDictService implements DictService {
    
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
        DictType getType();
    }
    
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

    @Override
    public List<String> getDictLabels(String dictCode) {
        return getDictLabels(dictCode, DictType.AUTO);
    }

    @Override
    public String getLabelByValue(String dictCode, String value) {
        return getLabelByValue(dictCode, value, DictType.AUTO);
    }

    @Override
    public String getValueByLabel(String dictCode, String label) {
        return getValueByLabel(dictCode, label, DictType.AUTO);
    }

    @Override
    public Map<String, String> getDictMap(String dictCode) {
        return getDictMap(dictCode, DictType.AUTO);
    }

    /**
     * 获取字典标签列表
     */
    public List<String> getDictLabels(String dictCode, DictType dictType) {
        return new ArrayList<>(getDictMap(dictCode, dictType).values());
    }

    /**
     * 获取字典值对应的标签
     */
    public String getLabelByValue(String dictCode, String value, DictType dictType) {
        return getDictMap(dictCode, dictType).get(value);
    }

    /**
     * 获取字典标签对应的值
     */
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
        if (dictType == DictType.AUTO) {
            return getAutoDict(dictCode);
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

        // 从提供者获取数据
        if (provider.support(dictCode)) {
            dictMap = provider.getDictData(dictCode);
            if (dictMap != null && !dictMap.isEmpty()) {
                dictCache.put(cacheKey, dictMap);
                return dictMap;
            }
        }

        return Collections.emptyMap();
    }

    /**
     * 自动模式获取字典数据
     */
    private Map<String, String> getAutoDict(String dictCode) {
        // 先从缓存获取
        Map<String, String> dictMap = dictCache.get(dictCode);
        if (dictMap != null) {
            return dictMap;
        }

        // 遍历所有提供者，找到支持的提供者
        for (DictProvider provider : providers) {
            if (provider.support(dictCode)) {
                dictMap = provider.getDictData(dictCode);
                if (dictMap != null && !dictMap.isEmpty()) {
                    // 放入缓存
                    dictCache.put(dictCode, dictMap);
                    return dictMap;
                }
            }
        }

        return Collections.emptyMap();
    }

    /**
     * 清除字典缓存
     */
    public void clearCache() {
        dictCache.clear();
    }

    /**
     * 清除指定字典的缓存
     */
    public void clearCache(String dictCode) {
        // 清除自动模式的缓存
        dictCache.remove(dictCode);
        // 清除指定类型的缓存
        for (DictType type : DictType.values()) {
            dictCache.remove(dictCode + ":" + type);
        }
    }
} 
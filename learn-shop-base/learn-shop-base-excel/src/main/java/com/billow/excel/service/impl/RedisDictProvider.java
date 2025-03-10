package com.billow.excel.service.impl;

import com.billow.excel.annotation.ExcelColumn.DictType;
import com.billow.excel.service.impl.CompositeDictService.DictProvider;
import com.billow.excel.spring.boot.autoconfigure.ExcelProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Redis缓存字典提供者
 */
@Order(3)
@Component
@RequiredArgsConstructor
public class RedisDictProvider implements DictProvider {

    private final StringRedisTemplate redisTemplate;
    private final ExcelProperties excelProperties;

    @Override
    public boolean support(String dictCode) {
        String key = excelProperties.getDictConfig().getRedisKeyPrefix() + dictCode;
        Boolean hasKey = redisTemplate.hasKey(key);
        return Boolean.TRUE.equals(hasKey);
    }

    @Override
    public Map<String, String> getDictData(String dictCode) {
        String key = excelProperties.getDictConfig().getRedisKeyPrefix() + dictCode;
        Map<Object, Object> entries = redisTemplate.opsForHash().entries(key);

        Map<String, String> dictMap = new HashMap<>();
        for (Map.Entry<Object, Object> entry : entries.entrySet()) {
            dictMap.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
        }

        return dictMap;
    }

    @Override
    public DictType getType() {
        return DictType.REDIS;
    }
}
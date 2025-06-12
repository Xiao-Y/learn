package com.billow.excel.provider.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.billow.excel.annotation.ExcelDict;
import com.billow.excel.config.ExcelProperties;
import com.billow.excel.provider.DictProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Redis缓存字典提供者
 */
@Order(2)
@RequiredArgsConstructor
public class RedisDictProvider implements DictProvider {

    private final StringRedisTemplate redisTemplate;
    private final ExcelProperties excelProperties;

    @Override
    public boolean support(String dictCode) {
        Object o = redisTemplate.opsForHash().get(excelProperties.getDict().getRedisKeyPrefix(), dictCode);
        return Objects.nonNull(o);
    }

    @Override
    public Map<String, String> getDictData(String dictCode) {
        Object o = redisTemplate.opsForHash().get(excelProperties.getDict().getRedisKeyPrefix(), dictCode);
        if (Objects.isNull(o)) {
            return new HashMap<>();
        }
        return JSON.parseArray(o.toString(), JSONObject.class)
                .stream()
                .collect(Collectors.toMap(m -> m.getString("fieldValue"), m -> m.getString("fieldDisplay")));
    }

    @Override
    public ExcelDict.DictType getType() {
        return ExcelDict.DictType.REDIS;
    }
}
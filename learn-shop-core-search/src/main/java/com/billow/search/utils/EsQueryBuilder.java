package com.billow.search.utils;

import cn.hutool.core.util.StrUtil;
import org.apache.commons.lang3.StringUtils;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ES 通用查询构建器
 * 支持多种查询方式：模糊查询、精确查询、范围查询、比较查询等
 *
 * @author billow
 * @since 2025-01-01
 */
public class EsQueryBuilder {

    private final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final String RANGE_SEPARATOR = "~";
    private final String MULTI_VALUE_SEPARATOR = ",";

    public static final EsQueryBuilder INSTANCE = new EsQueryBuilder();

    public static EsQueryBuilder getInstance() {
        return INSTANCE;
    }

    /**
     * 根据 JSON 参数构建 ES 查询条件
     *
     * @param wrapper ES 查询包装器
     * @param params  查询参数 Map
     * @param clazz   实体类 Class
     * @param <T>     实体类型
     * @return 构建后的查询包装器
     */
    public <T> LambdaEsQueryWrapper<T> buildQuery(LambdaEsQueryWrapper<T> wrapper, Map<String, String> params, Class<T> clazz) {
        if (params == null || params.isEmpty()) {
            return wrapper;
        }

        for (Map.Entry<String, String> entry : params.entrySet()) {
            String fieldName = entry.getKey();
            String value = entry.getValue();

            if (StringUtils.isBlank(value) || StringUtils.isBlank(fieldName)) {
                continue;
            }

            // 获取字段类型
            Field field = this.getField(clazz, fieldName);
            if (field == null) {
                continue;
            }

            // 根据值的格式判断查询类型
            this.applyCondition(wrapper, field, fieldName, value);
        }

        return wrapper;
    }

    /**
     * 应用查询条件
     */
    private <T> void applyCondition(LambdaEsQueryWrapper<T> wrapper, Field field, String fieldName, String value) {
        Class<?> fieldType = field.getType();

        // 1. 精确查询（以 = 开头）
        if (value.startsWith("=")) {
            String exactValue = value.substring(1);
            wrapper.eq(fieldName, this.convertValue(exactValue, fieldType));
            System.out.println("[EsQueryBuilder] 精确查询: " + fieldName + " = " + exactValue);
            return;
        }

        // 2. 分词查询（以 * 开头，用于宽松的分词匹配）
        if (value.startsWith("*")) {
            String matchValue = value.substring(1);
            // 使用 match 查询（OR 关系，匹配任意分词）
            wrapper.match(fieldName, matchValue);
            return;
        }

        // 3. 通配符查询（以 + 开头，强制使用 wildcard）
        if (value.startsWith("+")) {
            String wildcardValue = value.substring(1);
            wrapper.like(fieldName, wildcardValue);
            return;
        }

        // 4. 大于查询（以 > 开头）
        if (value.startsWith(">=")) {
            String compareValue = value.substring(2);
            wrapper.ge(fieldName, this.convertValue(compareValue, fieldType));
            return;
        }
        if (value.startsWith(">")) {
            String compareValue = value.substring(1);
            wrapper.gt(fieldName, this.convertValue(compareValue, fieldType));
            return;
        }

        // 5. 小于查询（以 < 开头）
        if (value.startsWith("<=")) {
            String compareValue = value.substring(2);
            wrapper.le(fieldName, this.convertValue(compareValue, fieldType));
            return;
        }
        if (value.startsWith("<")) {
            String compareValue = value.substring(1);
            wrapper.lt(fieldName, this.convertValue(compareValue, fieldType));
            return;
        }

        // 6. 范围查询（包含 ~）
        if (value.contains(RANGE_SEPARATOR)) {
            this.applyRangeQuery(wrapper, fieldName, value);
            return;
        }

        // 7. 多值查询（仅包含逗号分隔符）
        // 注意：只有包含逗号才认为是多值查询，避免将普通的带空格的句子误判为多值查询
        if (value.contains(MULTI_VALUE_SEPARATOR) && !value.contains(" ")) {
            value = value.replaceAll("\\s+", ",");
            this.applyMultiValueQuery(wrapper, fieldName, value, fieldType);
            return;
        }

        // 8. 默认查询
        // 对于字符串类型，检测是否包含中文，自动选择合适的查询方式
        if (fieldType == String.class) {
            // 检测是否包含中文字符
            if (this.containsChinese(value)) {
                // 中文内容使用 matchPhrase 查询（短语匹配，保持词序）
                wrapper.matchPhrase(fieldName, value);
            } else {
                // 英文/数字使用 like 查询（wildcard 查询）
                wrapper.like(fieldName, value);
            }
        } else {
            // 非字符串类型使用精确匹配
            wrapper.eq(fieldName, this.convertValue(value, fieldType));
        }
    }

    /**
     * 检测字符串是否包含中文字符
     */
    private boolean containsChinese(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        return str.matches(".*[\\u4e00-\\u9fa5]+.*");
    }

    /**
     * 应用范围查询
     */
    private <T> void applyRangeQuery(LambdaEsQueryWrapper<T> wrapper, String fieldName, String value) {
        String[] parts = value.split(RANGE_SEPARATOR, -1);

        String start = parts[0].trim();
        String end = parts[1].trim();

        // 时间范围查询
        if (StringUtils.isNotBlank(start)) {
            wrapper.ge(fieldName, start);
        }
        if (StringUtils.isNotBlank(end)) {
            wrapper.le(fieldName, end);
        }
    }

    /**
     * 应用多值查询（IN 查询或 OR 查询）
     * 注意：只使用逗号作为分隔符，不使用空格，避免将带空格的句子误判为多值查询
     */
    private <T> void applyMultiValueQuery(LambdaEsQueryWrapper<T> wrapper, String fieldName, String value, Class<?> fieldType) {
        // 使用逗号分隔
        List<String> values = Arrays.stream(value.split(MULTI_VALUE_SEPARATOR))
                .filter(StringUtils::isNotBlank)
                .map(String::trim)
                .collect(Collectors.toList());

        if (values.isEmpty()) {
            return;
        }


        // 对于字符串类型，检测是否包含中文
        if (fieldType == String.class && values.stream().anyMatch(this::containsChinese)) {
            // 中文多值查询：使用 should (OR) + matchPhrase
            for (int i = 0; i < values.size(); i++) {
                wrapper.matchPhrase(fieldName, values.get(i));
                if (i < values.size() - 1) {
                    wrapper.or();
                }
            }
//            wrapper.and(w -> {
//                for (int i = 0; i < values.size(); i++) {
//                    w.matchPhrase(fieldName, values.get(i));
//                    if (i < values.size() - 1) {
//                        w.or();
//                    }
//                }
//            });
        } else {
            // 非中文：使用 terms 查询（精确匹配）
            List<Object> convertedValues = values.stream()
                    .map(v -> this.convertValue(v, fieldType))
                    .collect(Collectors.toList());
            wrapper.in(fieldName, convertedValues);
        }
    }

    /**
     * 获取字段
     */
    private Field getField(Class<?> clazz, String fieldName) {
        try {
            // 转换驼峰命名
            String camelFieldName = StrUtil.toCamelCase(fieldName);
            return clazz.getDeclaredField(camelFieldName);
        } catch (NoSuchFieldException e) {
            // 尝试直接获取
            try {
                return clazz.getDeclaredField(fieldName);
            } catch (NoSuchFieldException ex) {
                return null;
            }
        }
    }

    /**
     * 判断是否为日期时间字段
     */
    private boolean isDateTimeField(Class<?> fieldType) {
        return LocalDateTime.class.isAssignableFrom(fieldType)
                || java.util.Date.class.isAssignableFrom(fieldType)
                || java.sql.Date.class.isAssignableFrom(fieldType)
                || java.sql.Timestamp.class.isAssignableFrom(fieldType);
    }

    /**
     * 解析日期时间
     */
    private LocalDateTime parseDateTime(String dateTimeStr) {
        try {
            return LocalDateTime.parse(dateTimeStr, DATE_TIME_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("日期时间格式错误，正确格式为：yyyy-MM-dd HH:mm:ss");
        }
    }

    /**
     * 转换值为对应类型
     */
    private Object convertValue(String value, Class<?> targetType) {
        if (StringUtils.isBlank(value)) {
            return null;
        }

        try {
            if (targetType == String.class) {
                return value;
            } else if (targetType == Integer.class || targetType == int.class) {
                return Integer.parseInt(value);
            } else if (targetType == Long.class || targetType == long.class) {
                return Long.parseLong(value);
            } else if (targetType == Double.class || targetType == double.class) {
                return Double.parseDouble(value);
            } else if (targetType == Float.class || targetType == float.class) {
                return Float.parseFloat(value);
            } else if (targetType == Boolean.class || targetType == boolean.class) {
                return Boolean.parseBoolean(value);
            } else if (isDateTimeField(targetType)) {
                return this.parseDateTime(value);
            }
            return value;
        } catch (Exception e) {
            return value;
        }
    }
}

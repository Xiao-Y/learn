package com.billow.search.utils;

import org.apache.commons.lang3.StringUtils;
import org.dromara.easyes.core.conditions.select.LambdaEsQueryWrapper;

import java.lang.reflect.Field;
import java.util.Objects;

public class LambdaEsQueryWrapperUtils {

    /**
     * 通用方法：根据对象中的非空属性动态构建查询条件
     * 当对象中属性不为空时，就拼接条件
     *
     * @param wrapper 查询包装器
     * @param obj 包含查询条件的对象
     * @param <T> 泛型类型
     * @return LambdaEsQueryWrapper
     */
    public static <T> LambdaEsQueryWrapper<T> buildQueryWrapper(LambdaEsQueryWrapper<T> wrapper, T obj) {
        if (obj == null) {
            return wrapper;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                
                // 只有当值不为空时才添加查询条件
                if (Objects.nonNull(value)) {
                    // 对于字符串类型，还需要确保不为空字符串
                    if (value instanceof String) {
                        if (StringUtils.isNotBlank((String) value)) {
                            wrapper.eq(field.getName(), value);
                        }
                    } else {
                        wrapper.eq(field.getName(), value);
                    }
                }
            } catch (IllegalAccessException e) {
                // 忽略无法访问的字段
                e.printStackTrace();
            }
        }
        
        return wrapper;
    }

    /**
     * 更加灵活的通用方法：支持自定义条件判断
     *
     * @param wrapper 查询包装器
     * @param obj 包含查询条件的对象
     * @param conditionChecker 条件检查器，用于判断是否应该添加某个字段作为查询条件
     * @param <T> 泛型类型
     * @return LambdaEsQueryWrapper
     */
    public static <T> LambdaEsQueryWrapper<T> buildQueryWrapper(
            LambdaEsQueryWrapper<T> wrapper, 
            T obj, 
            ConditionChecker conditionChecker) {
        
        if (obj == null) {
            return wrapper;
        }

        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.setAccessible(true);
                Object value = field.get(obj);
                
                // 使用自定义条件检查器判断是否应该添加查询条件
                if (conditionChecker.shouldAddCondition(field, value)) {
                    wrapper.eq(getFieldName(field), value);
                }
            } catch (IllegalAccessException e) {
                // 忽略无法访问的字段
                e.printStackTrace();
            }
        }
        
        return wrapper;
    }

    /**
     * 获取字段名（可以通过注解等方式自定义字段名映射）
     *
     * @param field 字段
     * @return 字段名
     */
    private static String getFieldName(Field field) {
        return field.getName();
    }

    /**
     * 条件检查器接口
     */
    public interface ConditionChecker {
        /**
         * 判断是否应该将字段添加为查询条件
         *
         * @param field 字段
         * @param value 字段值
         * @return 是否应该添加为查询条件
         */
        boolean shouldAddCondition(Field field, Object value);
    }

    /**
     * 默认条件检查器实现
     */
    public static class DefaultConditionChecker implements ConditionChecker {
        @Override
        public boolean shouldAddCondition(Field field, Object value) {
            if (value == null) {
                return false;
            }
            
            // 字符串类型需要特殊处理
            if (value instanceof String) {
                return StringUtils.isNotBlank((String) value);
            }
            
            return true;
        }
    }
}

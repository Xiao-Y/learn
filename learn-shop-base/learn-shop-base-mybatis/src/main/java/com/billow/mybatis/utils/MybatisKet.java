package com.billow.mybatis.utils;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author liuyongtao
 * @since 2021-4-1 15:45
 */
public class MybatisKet {

    public static <T> QueryWrapper<T> getCondition(T t) {
        QueryWrapper<T> query = Wrappers.query();
        try {
            LinkedHashMap<String, Object> map = entityToMap(t, false);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                query.eq(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }

    public static <T> QueryWrapper<T> getConditionLike(T t) {
        QueryWrapper<T> query = Wrappers.query();
        try {
            LinkedHashMap<String, Object> map = entityToMap(t, false);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                query.like(entry.getKey(), entry.getValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }


    /**
     * entity转换成map
     * key为属性名,value为值
     *
     * @param t
     * @param insertEmpty 值为空时是不加入到map 中
     * @return {@link LinkedHashMap< String, Object>}
     * @author liuyongtao
     * @since 2021-4-1 16:00
     */
    public static <T> LinkedHashMap<String, Object> entityToMap(T t, boolean insertEmpty) throws Exception {
        LinkedHashMap<String, Object> map = new LinkedHashMap<>();

        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            TableField annotation = field.getAnnotation(TableField.class);
            String name = field.getName();
            if (StringUtils.isEmpty(name) || annotation == null || "serialVersionUID".equalsIgnoreCase(name)) {
                continue;
            }
            String column = annotation.value();
            if (StringUtils.isNotEmpty(column)) {
                name = column;
            }
            Object value = field.get(t);
            if (insertEmpty || (!insertEmpty && value != null)) {
                map.put(name, value);
            }
        }
        return map;
    }
}

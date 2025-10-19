package com.billow.mybatis.utils;

import cn.hutool.core.util.StrUtil;
import com.billow.mybatis.pojo.BasePage;
import com.mybatisflex.annotation.Column;
import com.mybatisflex.core.query.QueryWrapper;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author liuyongtao
 * @since 2021-4-1 15:45
 */
public class MybatisKet {

    public static <T> QueryWrapper getCondition(T t) {
        QueryWrapper query = QueryWrapper.create();
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

    public static <T extends BasePage> QueryWrapper getConditionLike(T t) {
        QueryWrapper query = QueryWrapper.create();
        try {
            LinkedHashMap<String, Object> map = entityToMap(t, false);
            for (Map.Entry<String, Object> entry : map.entrySet()) {
                query.like(entry.getKey(), entry.getValue());
            }
            // 排序
            addSortBy(t, query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return query;
    }

    /**
     * 添加排序
     *
     * @param t
     * @param query
     * @param <T>
     */
    public static <T extends BasePage> void addSortBy(T t, QueryWrapper query) {
        if (StringUtils.isNotEmpty(t.getOrderBy())) {
            String orderBy = SqlUtil.escapeOrderBySql(t.getOrderBy());
            List<String> orderByList = StrUtil.split(orderBy, ",");
            List<String> ascList = StrUtil.split(t.getIsAsc(), ",");
            for (int i = 0; i < orderByList.size(); i++) {
                query.orderBy(orderByList.get(i), ascList.get(i));

            }
        }
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
            Column annotation = field.getAnnotation(Column.class);
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

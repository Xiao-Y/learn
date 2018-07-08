package com.billow.common.utils;

import com.billow.tools.utlis.ToolsUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * 查询工具类
 */
public class QueryUtils {

    /**
     * 左like
     *
     * @param value
     * @return
     */
    public static String lLkei(String value) {
        return "%" + value;
    }

    /**
     * 右like
     *
     * @param value
     * @return
     */
    public static String rLkei(String value) {
        return value + "%";
    }

    /**
     * 全like
     *
     * @param value
     * @return
     */
    public static String aLike(String value) {
        return "%" + value + "%";
    }

    /**
     * 获取查询条件（不为空的）
     *
     * @param root
     * @param criteriaBuilder
     * @param po
     * @param <T>
     * @return
     * @throws Exception
     */
    public static <T> List<Predicate> getPredicates(Root<T> root, CriteriaBuilder criteriaBuilder, T po) {
        List<Predicate> list = new ArrayList<>();
        // po主类的
        Class<?> clazz = po.getClass();
        getFieldsValue(root, criteriaBuilder, po, list, clazz);
        // base类的
        Class<?> superClazz = clazz.getSuperclass();
        getFieldsValue(root, criteriaBuilder, po, list, superClazz);
        return list;
    }

    /**
     * 获取属性中的值
     *
     * @param root
     * @param criteriaBuilder
     * @param po
     * @param list
     * @param clazz
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void getFieldsValue(Root<T> root, CriteriaBuilder criteriaBuilder, T po, List<Predicate> list, Class<?> clazz) {
        try {
            Field[] fields = clazz.getDeclaredFields();
            if (ToolsUtils.isNotEmpty(fields)) {
                for (Field field : fields) {
                    String fieldName = field.getName();
                    if (fieldName.equals("serialVersionUID")) {
                        continue;
                    }
                    field.setAccessible(true);
                    Object fieldValue = field.get(po);
                    if (ToolsUtils.isNotEmpty(fieldValue)) {
                        Class<?> type = field.getType();

                        list.add(criteriaBuilder.equal(root.get(fieldName).as(type), fieldValue));
                    }
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}


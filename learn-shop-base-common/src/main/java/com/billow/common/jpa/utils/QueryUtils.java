package com.billow.common.jpa.utils;


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
     * 获取查询条件（不为空的）,并且设置 po 中的 fieldName 为 null
     *
     * @param root
     * @param criteriaBuilder
     * @param po              数据源(只能是po)
     * @param <T>
     * @throws Exception
     */
    public static <T> void getPredicateLike(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder, T po, String fieldName) {
        // po主类的
        Class<?> clazz = po.getClass();
        boolean flag = getFieldValueLike(root, criteriaBuilder, po, predicates, clazz, fieldName);
        if (!flag) {
            // base类的
            Class<?> superClazz = clazz.getSuperclass();
            getFieldValueLike(root, criteriaBuilder, po, predicates, superClazz, fieldName);
        }
    }

    /**
     * 获取查询条件（不为空的）
     *
     * @param root
     * @param criteriaBuilder
     * @param po              数据源(只能是po)
     * @param <T>
     * @throws Exception
     */
    public static <T> void getPredicates(List<Predicate> list, Root<T> root, CriteriaBuilder criteriaBuilder, T po) {
        // po主类的
        Class<?> clazz = po.getClass();
        getFieldsValueEqual(root, criteriaBuilder, po, list, clazz);
        // base类的
        Class<?> superClazz = clazz.getSuperclass();
        getFieldsValueEqual(root, criteriaBuilder, po, list, superClazz);
    }

    /**
     * 获取查询条件（不为空的）
     *
     * @param root
     * @param criteriaBuilder
     * @param po              数据源(只能是po)
     * @param <T>
     * @return List Predicate
     * @throws Exception
     */
    public static <T> List<Predicate> getPredicates(Root<T> root, CriteriaBuilder criteriaBuilder, T po) {
        List<Predicate> list = new ArrayList<>();
        getPredicates(list, root, criteriaBuilder, po);
        return list;
    }

    /**
     * 获取属性中的值
     *
     * @param root
     * @param criteriaBuilder
     * @param predicates
     * @param po              数据源
     * @param clazz           当前类或者基类
     * @param <T>
     * @throws IllegalAccessException
     */
    private static <T> void getFieldsValueEqual(Root<T> root, CriteriaBuilder criteriaBuilder, T po, List<Predicate> predicates, Class<?> clazz) {
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
                        predicates.add(criteriaBuilder.equal(root.get(fieldName).as(type), fieldValue));
                    }
                }
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 添加单个 Predicate 到 predicates，并且设置 po 中的 fieldName 为 null
     *
     * @param root
     * @param criteriaBuilder
     * @param predicates
     * @param po              数据源
     * @param clazz           当前类或者基类
     * @param fieldName       获取的字段
     * @param <T>
     * @return 是否找到当前字段
     */
    private static <T> boolean getFieldValueLike(Root<T> root, CriteriaBuilder criteriaBuilder, T po, List<Predicate> predicates, Class<?> clazz, String fieldName) {
        boolean flag = false;
        try {
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            Object fieldValue = field.get(po);
            flag = true;
            if (ToolsUtils.isNotEmpty(fieldValue)) {
                predicates.add(criteriaBuilder.like(root.get(fieldName).as(String.class), fieldValue.toString()));
                field.set(po, null);
            }
        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
        return flag;
    }
}


//package com.billow.common.jpa.utils;
//
//
//import com.billow.tools.utlis.ToolsUtils;
//
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.Predicate;
//import javax.persistence.criteria.Root;
//import java.lang.reflect.Field;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * 查询工具类
// */
//public class QueryUtils {
//
//    private static final String L_LIKE = "lLkei";
//    private static final String R_LIKE = "rLike";
//    private static final String A_LIKE = "aLike";
//    private static final String EQUAL = "equal";
//
//    /**
//     * List转Array
//     *
//     * @param all
//     * @return javax.persistence.criteria.Predicate[]
//     * @author LiuYongTao
//     * @date 2019/8/22 15:56
//     */
//    public static Predicate[] converListToArray(List<Predicate> all) {
//        Predicate[] predicates = new Predicate[all.size()];
//        all.toArray(predicates);
//        return predicates;
//    }
//
//    /**
//     * 获取查询条件（指定字段，aLike）,并且设置 po 中的 fieldName 为 null
//     *
//     * @param root
//     * @param criteriaBuilder
//     * @param po              数据源(只能是po)
//     * @param <T>
//     * @throws Exception
//     */
//    public static <T> void getPredicateALike(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder, T po, String fieldName) {
//        getFieldValue(predicates, root, criteriaBuilder, po, fieldName, A_LIKE);
//    }
//
//    /**
//     * 获取查询条件（指定字段，lLike）,并且设置 po 中的 fieldName 为 null
//     *
//     * @param root
//     * @param criteriaBuilder
//     * @param po              数据源(只能是po)
//     * @param <T>
//     * @throws Exception
//     */
//    public static <T> void getPredicateLLike(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder, T po, String fieldName) {
//        getFieldValue(predicates, root, criteriaBuilder, po, fieldName, L_LIKE);
//    }
//
//    /**
//     * 获取查询条件（指定字段，rLike）,并且设置 po 中的 fieldName 为 null
//     *
//     * @param root
//     * @param criteriaBuilder
//     * @param po              数据源(只能是po)
//     * @param <T>
//     * @throws Exception
//     */
//    public static <T> void getPredicateRLike(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder, T po, String fieldName) {
//        getFieldValue(predicates, root, criteriaBuilder, po, fieldName, R_LIKE);
//    }
//
//    /**
//     * 获取查询条件（指定字段，equal）,并且设置 po 中的 fieldName 为 null
//     *
//     * @param root
//     * @param criteriaBuilder
//     * @param po              数据源(只能是po)
//     * @param <T>
//     * @throws Exception
//     */
//    public static <T> void getPredicateEqual(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder, T po, String fieldName) {
//        getFieldValue(predicates, root, criteriaBuilder, po, fieldName, EQUAL);
//    }
//
//    /**
//     * 获取查询条件（不为空的）
//     *
//     * @param root
//     * @param criteriaBuilder
//     * @param po              数据源(只能是po)
//     * @param <T>
//     * @return List Predicate
//     * @throws Exception
//     */
//    public static <T> List<Predicate> getPredicateEqual(Root<T> root, CriteriaBuilder criteriaBuilder, T po) {
//        List<Predicate> predicates = new ArrayList<>();
//        getPredicateEqual(predicates, root, criteriaBuilder, po);
//        return predicates;
//    }
//
//    /**
//     * 获取查询条件（不为空的）
//     *
//     * @param root
//     * @param criteriaBuilder
//     * @param predicates
//     * @param po              数据源(只能是po)
//     * @param <T>
//     * @throws IllegalAccessException
//     */
//    public static <T> void getPredicateEqual(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder, T po) {
//        boolean flag = false;
//        try {
//            // po主类的
//            Class<?> clazz = po.getClass();
//            Field[] fields = clazz.getDeclaredFields();
//            if (ToolsUtils.isNotEmpty(fields)) {
//                for (Field field : fields) {
//                    flag = getFieldValue(root, criteriaBuilder, po, predicates, clazz, field.getName(), EQUAL);
//                }
//            }
//            // base类的
//            Class<?> superClazz = clazz.getSuperclass();
//            Field[] superFields = superClazz.getDeclaredFields();
//            if (ToolsUtils.isNotEmpty(superFields)) {
//                for (Field superField : superFields) {
//                    getFieldValue(root, criteriaBuilder, po, predicates, superClazz, superField.getName(), EQUAL);
//                }
//            }
//        } catch (SecurityException | IllegalArgumentException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * @param predicates
//     * @param root
//     * @param criteriaBuilder
//     * @param po              数据源
//     * @param clazz           当前类或者基类
//     * @param symbol          查询类型：A_LIKE,L_LIKE,R_LIKE,EQUAL
//     * @return void
//     * @author LiuYongTao
//     * @date 2019/8/22 15:14
//     */
//    private static <T> void getFieldValue(List<Predicate> predicates, Root<T> root, CriteriaBuilder criteriaBuilder, T po, String fieldName, String symbol) {
//        // po主类的
//        Class<?> clazz = po.getClass();
//        boolean flag = getFieldValue(root, criteriaBuilder, po, predicates, clazz, fieldName, symbol);
//        if (!flag) {
//            // base类的
//            Class<?> superClazz = clazz.getSuperclass();
//            getFieldValue(root, criteriaBuilder, po, predicates, superClazz, fieldName, symbol);
//        }
//    }
//
//    /**
//     * 添加单个 Predicate 到 predicates，并且设置 po 中的 fieldName 为 null
//     *
//     * @param root
//     * @param criteriaBuilder
//     * @param predicates
//     * @param po              数据源
//     * @param clazz           当前类或者基类
//     * @param fieldName       获取的字段
//     * @param symbol          查询类型：A_LIKE,L_LIKE,R_LIKE,EQUAL
//     * @param <T>
//     * @return 是否找到当前字段
//     */
//    private static <T> boolean getFieldValue(Root<T> root, CriteriaBuilder criteriaBuilder, T po, List<Predicate> predicates, Class<?> clazz, String fieldName, String symbol) {
//        if (fieldName.equals("serialVersionUID")) {
//            return true;
//        }
//        boolean flag = false;
//        try {
//            Field field = clazz.getDeclaredField(fieldName);
//            field.setAccessible(true);
//            Object fieldValue = field.get(po);
//            flag = true;
//            if (ToolsUtils.isNotEmpty(fieldValue)) {
//                if (A_LIKE.equals(symbol)) {
//                    predicates.add(criteriaBuilder.like(root.get(fieldName).as(String.class), QueryUtils.aLike(fieldValue.toString())));
//                } else if (L_LIKE.equals(symbol)) {
//                    predicates.add(criteriaBuilder.like(root.get(fieldName).as(String.class), QueryUtils.lLike(fieldValue.toString())));
//                } else if (R_LIKE.equals(symbol)) {
//                    predicates.add(criteriaBuilder.like(root.get(fieldName).as(String.class), QueryUtils.rLike(fieldValue.toString())));
//                } else if (EQUAL.equals(symbol)) {
//                    predicates.add(criteriaBuilder.equal(root.get(fieldName).as(field.getType()), fieldValue));
//                }
//                field.set(po, null);
//            }
//        } catch (SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchFieldException e) {
//            e.printStackTrace();
//        }
//        return flag;
//    }
//
//    /**
//     * 左like
//     *
//     * @param value
//     * @return
//     */
//    private static String lLike(String value) {
//        return "%" + value;
//    }
//
//    /**
//     * 右like
//     *
//     * @param value
//     * @return
//     */
//    private static String rLike(String value) {
//        return value + "%";
//    }
//
//    /**
//     * 全like
//     *
//     * @param value
//     * @return
//     */
//    private static String aLike(String value) {
//        return "%" + value + "%";
//    }
//}
//

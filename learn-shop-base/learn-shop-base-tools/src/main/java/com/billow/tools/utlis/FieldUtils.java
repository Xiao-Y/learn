package com.billow.tools.utlis;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

/**
 * 填充共有字段
 *
 * @author LiuYongTao
 * @date 2018/4/27 12:47
 */
public class FieldUtils {

    private static Logger logger = LoggerFactory.getLogger(FieldUtils.class);

    public final static String CREATOR_CODE = "creatorCode";
    public final static String CREATE_TIME = "createTime";
    public final static String UPDATER_CODE = "updaterCode";
    public final static String UPDATE_TIME = "updateTime";
    public final static String VALID_IND = "validInd";

    public final static String REQUEST_URL = "requestUrl";
    public final static String PAGE_SIZE = "pageSize";
    public final static String PAGE_NO = "pageNo";
    public final static String RECORD_COUNT = "recordCount";
    public final static String OBJECT_ORDER_BY = "objectOrderBy";

    /**
     * 插入数据时
     *
     * @param obj
     * @param userCode
     * @return void
     * @author LiuYongTao
     * @date 2018/4/27 12:47
     */
    public static void setCommonFieldByInsert(Object obj, String userCode) {
        Date date = new Date();
        setValue(obj, CREATOR_CODE, userCode);
        setValue(obj, CREATE_TIME, date);
        setValue(obj, UPDATER_CODE, userCode);
        setValue(obj, UPDATE_TIME, date);
    }

    /**
     * 更新数据时
     *
     * @param obj
     * @param userCode
     * @return void
     * @author LiuYongTao
     * @date 2018/4/27 12:47
     */
    public static void setCommonFieldByUpdate(Object obj, String userCode) {
        Date date = new Date();
        setValue(obj, UPDATER_CODE, userCode);
        setValue(obj, UPDATE_TIME, date);
    }

    /**
     * 插入数据时，带有效标志的
     *
     * @param obj
     * @param userCode
     * @return void
     * @author LiuYongTao
     * @date 2018/5/16 8:45
     */
    public static void setCommonFieldByInsertWithValidInd(Object obj, String userCode) {
        Date date = new Date();
        Boolean b = true;
        setValue(obj, CREATOR_CODE, userCode);
        setValue(obj, CREATE_TIME, date);
        setValue(obj, UPDATER_CODE, userCode);
        setValue(obj, UPDATE_TIME, date);
        setValue(obj, VALID_IND, b);
    }

    /**
     * 对象设置属性值
     *
     * @param obj       源对象
     * @param fieldName 设置属性
     * @param value     设置值
     * @return void
     * @author billow
     * @date 2019/7/20 15:34
     */
    public static void setValue(Object obj, String fieldName, Object value) {
        if (obj.getClass().getSimpleName().equals(Object.class.getSimpleName())) {
//            logger.warn("{} 中未找到属性 {},setter fail...", obj.getClass().getName(), fieldName, value);
            return;
        }
        setValue(obj, obj.getClass(), fieldName, value);
    }

    /**
     * 对象设置属性值
     *
     * @param obj       源对象
     * @param clazz     需要查找的对象（多层继承关系时，从那一层开始找）
     * @param fieldName 设置属性
     * @param value     设置值
     * @return void
     * @author billow
     * @date 2019/7/20 15:34
     */
    public static void setValue(Object obj, Class<?> clazz, String fieldName, Object value) {
        if (clazz.getSimpleName().equals(Object.class.getSimpleName())) {
//            logger.warn("{} 中未找到属性 {},set fail...", obj.getClass().getName(), fieldName, value);
            return;
        }
        try {
            Field field = null;
            boolean fieldFlag = false;
            Field[] fields = clazz.getDeclaredFields();
            if (fields == null || fields.length < 1) {
                setValue(obj, clazz.getSuperclass(), fieldName, value);
            }

            for (Field item : fields) {
                if (item.getName().equals(fieldName)) {
                    fieldFlag = true;
                    field = item;
                    break;
                }
            }

            if (!fieldFlag) {
                setValue(obj, clazz.getSuperclass(), fieldName, value);
            } else {
                field.setAccessible(true);
                field.set(obj, value);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Class<?> getSuperclassByTrgClassName(Class<?> clazz, String trgClassName) {
        Class<?> basePo;
        String simpleName = clazz.getSimpleName();
        if (simpleName.equals(Object.class.getSimpleName())) {
            return null;
        }
        if (trgClassName.equals(simpleName)) {
            return clazz;
        } else {
            basePo = getSuperclassByTrgClassName(clazz.getSuperclass(), trgClassName);
        }
        return basePo;
    }

    /**
     * 获取指定属性的值
     *
     * @return java.util.HashMap<java.lang.String, java.lang.Object>
     * @author LiuYongTao
     * @date 2018/7/10 9:08
     */
    public static <T> HashMap<String, Object> entityToMap(T t, Set<String> setTitles) throws NoSuchFieldException, IllegalAccessException {

        HashMap<String, Object> map = new HashMap<>();

        Class<?> clazz = t.getClass();

        for (String titleCode : setTitles) {
            if (ToolsUtils.isNotEmpty(titleCode)) {
                Field field = clazz.getDeclaredField(titleCode);
                field.setAccessible(true);
                String name = field.getName();
                Object value = field.get(t);
                map.put(name, value);
            }

        }
        return map;
    }

    /**
     * 反射设置值,value 会转成对应的类型
     *
     * @param t
     * @param fieldName
     * @param value
     * @author liuyongtao
     * @since 2021-9-2 21:11
     */
    public static <T> void setStrValue(T t, String fieldName, String value) {
        Object obj;
        try {
            obj = converValue(t, fieldName, value);
        } catch (Exception e) {
            return;
        }
        Field field = ReflectionUtils.findField(t.getClass(), fieldName);
        if (field == null) {
            return;
        }
        field.setAccessible(true);
        try {
            field.set(t, obj);
        } catch (IllegalAccessException e) {
            logger.error("set 异常:fieldName:{},value:{}", fieldName, value);
        }
    }

    /**
     * string 转换为对应的类型
     *
     * @param t
     * @param fieldName
     * @param value
     * @return {@link Object}
     * @author liuyongtao
     * @since 2021-9-2 21:13
     */
    public static <T> Object converValue(T t, String fieldName, String value) throws Exception {
        Field field = ReflectionUtils.findField(t.getClass(), fieldName);
        if (field == null) {
            throw new NoSuchFieldException("fieldName:" + fieldName + "不存在");
        }
        Object obj = null;
        if (StringUtils.isNotBlank(value)) {
            Class<?> type = field.getType();
            if (type.isAssignableFrom(Long.class)) {
                obj = new Long(value);
            } else if (type.isAssignableFrom(Integer.class)) {
                obj = new Integer(value);
            } else if (type.isAssignableFrom(String.class)) {
                obj = value;
            }
        }
        return obj;
    }
}

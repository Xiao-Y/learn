package com.ft.utlis;

import java.lang.reflect.Field;
import java.util.Date;

/**
 * 填充共有字段
 *
 * @author LiuYongTao
 * @date 2018/4/27 12:47
 */
public class FieldUtils {

    private static Class<?> basePo;

    /**
     * 插入数据时
     *
     * @param [obj, userCode]
     * @return void
     * @author LiuYongTao
     * @date 2018/4/27 12:47
     */
    public static void setCommonFieldByInsert(Object obj, String userCode) throws Exception {
        Date date = new Date();
        setValue(obj, "creatorCode", userCode);
        setValue(obj, "createTime", date);
        setValue(obj, "updaterCode", userCode);
        setValue(obj, "updateTime", date);
    }

    /**
     * 更新数据时
     *
     * @param [obj, userCode]
     * @return void
     * @author LiuYongTao
     * @date 2018/4/27 12:47
     */
    public static void setCommonFieldByUpdate(Object obj, String userCode) throws Exception {
        Date date = new Date();
        setValue(obj, "updaterCode", userCode);
        setValue(obj, "updateTime", date);
    }

    /**
     * 插入数据时，带有效标志的
     *
     * @param [obj, userCode]
     * @return void
     * @author LiuYongTao
     * @date 2018/5/16 8:45
     */
    public static void setCommonFieldByInsertWithValidInd(Object obj, String userCode) throws Exception {
        Date date = new Date();
        Boolean b = true;
        setValue(obj, "creatorCode", userCode);
        setValue(obj, "createTime", date);
        setValue(obj, "updaterCode", userCode);
        setValue(obj, "updateTime", date);
        setValue(obj, "validInd", b);
    }

    private static void setValue(Object obj, String FieldName, Object value) throws Exception {

        if (null == basePo) {
            basePo = getBasePo(obj.getClass());
        }
        Field field = basePo.getDeclaredField(FieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

    private static Class<?> getBasePo(Class<?> clazz) {
        Class<?> basePo;
        String simpleName = clazz.getSimpleName();
        if ("BasePo".equals(simpleName)) {
            return clazz;
        } else {
            basePo = getBasePo(clazz.getSuperclass());
        }
        return basePo;
    }
}

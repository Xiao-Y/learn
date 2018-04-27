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

    private static void setValue(Object obj, String FieldName, Object value) throws Exception {
        Field field = obj.getClass().getDeclaredField(FieldName);
        field.setAccessible(true);
        field.set(obj, value);
    }

}

package com.billow.email.utils;

/**
 * @author liuyongtao
 * @create 2019-09-17 12:19
 */
public class ToolsUtils {

    public static boolean isEmpty(String str) {
        if (str == null || "".equals(str.trim())) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        return !isEmpty(str);
    }
}

package com.billow.job.util;

import java.util.List;

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

    public static <T> boolean isEmpty(List<T> list) {
        if (list == null || list.size() < 1) {
            return true;
        }
        return false;
    }

    public static <T> boolean isNotEmpty(List<T> list) {
        return !isEmpty(list);
    }
}

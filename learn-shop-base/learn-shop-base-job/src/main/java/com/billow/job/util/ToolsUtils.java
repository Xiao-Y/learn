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

    /**
     * 计算总页数
     *
     * @param pageSize
     * @param total
     * @return int
     * @author LiuYongTao
     * @date 2019/12/19 15:56
     */
    public static int getPages(Integer pageSize, Integer total) {
        if (pageSize == 0) {
            return 0;
        }
        int pages = total / pageSize;
        if (total % pageSize != 0) {
            pages++;
        }
        return pages;
    }
}

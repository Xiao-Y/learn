package com.billow.mybatis.utils;

import org.apache.commons.lang3.StringUtils;

public class SqlUtil {
    public static String SQL_PATTERN = "[a-zA-Z0-9_\\ \\,\\.]+";

    public SqlUtil() {
    }

    public static String escapeOrderBySql(String value) {
        if (StringUtils.isNotEmpty(value) && !isValidOrderBySql(value)) {
            throw new RuntimeException("参数不符合规范，不能进行查询");
        } else {
            return value;
        }
    }

    public static boolean isValidOrderBySql(String value) {
        return value.matches(SQL_PATTERN);
    }
}
package com.billow.search.utils;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.TimeZone;

public class EsUtils {

    /**
     * 获取当前时间 yyyy-MM-dd HH:mm:ss
     *
     * @param
     * @return String
     * @author 千面
     * @date 2022/8/9 9:28
     */
    public static String getStrNow() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        return df.format(new Date());
    }

    /**
     * 获取当前时间
     *
     * @param
     * @return LocalDateTime
     * @author 千面
     * @date 2022/8/9 9:28
     */
    public static LocalDateTime getLocalNow() {
        return LocalDateTime.now(ZoneOffset.UTC);
    }
}

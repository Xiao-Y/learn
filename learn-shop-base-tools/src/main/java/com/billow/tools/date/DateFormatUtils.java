package com.billow.tools.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatUtils {
    private static final String DAY_FORMAT_STRING = "yyyyMMdd";
    private static SimpleDateFormat DAY_FORMAT = new SimpleDateFormat(DAY_FORMAT_STRING);
    private static SimpleDateFormat FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    public static String getDayFormatString(Date date) {
        if (date == null) {
            throw new IllegalArgumentException("date can not be null");
        }
        return DAY_FORMAT.format(date).toString();
    }

    /**
     * 字符串转化为日期格式
     *
     * @param strDate
     * @param simpleDateFormat
     * @return
     * @throws Exception
     */
    public static Date convertStringToDate(String strDate, SimpleDateFormat simpleDateFormat) throws Exception {
        Date date = null;
        if (strDate == null || "".equals(strDate)) {
            return date;
        }

        if (simpleDateFormat == null) {
            simpleDateFormat = FORMAT;
        }
        try {
            date = FORMAT.parse(strDate);
        } catch (Exception e) {
            throw new Exception("日期格式转化异常:" + e.getMessage());
        }
        return date;
    }

    /**
     * 日期格式转化为字符串
     *
     * @param date
     * @param simpleDateFormat
     * @return
     */
    public static String convertDateToString(Date date, SimpleDateFormat simpleDateFormat) {
        String strDate = null;
        if (date == null) {
            return strDate;
        }
        if (simpleDateFormat == null) {
            simpleDateFormat = FORMAT;
        }
        return FORMAT.format(date);
    }

    /**
     * long 转 天/时/分/秒
     *
     * @param time
     * @return java.lang.String
     * @author LiuYongTao
     * @date 2019/8/15 17:15
     */
    public static String covertLongToString(long time) {
        long days = time / (1000 * 60 * 60 * 24);
        long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (time % (1000 * 60)) / 1000;
        return days + "天" + hours + "小时" + minutes + "分" + seconds + "秒";
    }
}

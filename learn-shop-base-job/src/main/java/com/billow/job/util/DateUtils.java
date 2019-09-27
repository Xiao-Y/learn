package com.billow.job.util;

/**
 * @author liuyongtao
 * @create 2019-09-26 15:18
 */
public class DateUtils {

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

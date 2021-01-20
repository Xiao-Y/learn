package com.billow.job.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 订单号生成工具，生成非重复订单号，理论上限1毫秒1000个，可扩展
 *
 * @author liuyongtao
 * @create 2017-10-24 17:53
 */
public class NumUtil {
    /**
     * 锁对象，可以为任意对象
     */
    private static Object lockObj = "lockerOrder";
    /**
     * 订单号生成计数器
     */
    private static long orderNumCount = 0L;
    /**
     * 每毫秒生成订单号数量最大值
     */
    private static int maxPerMSECSize = 1000;

    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     */
    public static String makeNum(String pix) {
        // 最终生成的订单号
        String finOrderNum = "";
        synchronized (lockObj) {
            // 取系统当前时间作为订单号变量前半部分，精确到毫秒
            long nowLong = Long.parseLong(new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date()));
            // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
            if (orderNumCount >= maxPerMSECSize) {
                orderNumCount = 0L;
            }
            //组装订单号
            String countStr = maxPerMSECSize + orderNumCount + "";
            finOrderNum = pix + nowLong + countStr.substring(1);
            orderNumCount++;
        }
        return finOrderNum;
    }
}

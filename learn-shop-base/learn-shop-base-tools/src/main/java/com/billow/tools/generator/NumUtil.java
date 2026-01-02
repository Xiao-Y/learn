package com.billow.tools.generator;

import cn.hutool.core.util.StrUtil;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
     * 订单号中时间部分的格式
     */
    private static final String ORDER_TIME_FORMAT = "yyyyMMddHHmmssSSS";
    
    /**
     * 日期时间格式化器
     */
    private static final DateTimeFormatter DATETIME_FORMATTER = DateTimeFormatter.ofPattern(ORDER_TIME_FORMAT);

    /**
     * 默认生成前缀为B，生成非重复订单号，理论上限1毫秒1000个，可扩展
     */
    public static String makeNum() {
        // 最终生成的订单号
        return makeNum("B");
    }

    /**
     * 生成非重复订单号，理论上限1毫秒1000个，可扩展
     */
    public static String makeNum(String pix) {
        // 最终生成的订单号
        String finOrderNum = "";
        synchronized (lockObj) {
            // 取系统当前时间作为订单号变量前半部分，精确到毫秒
            long nowLong = Long.parseLong(new SimpleDateFormat(ORDER_TIME_FORMAT).format(new Date()));
            // 计数器到最大值归零，可扩展更大，目前1毫秒处理峰值1000个，1秒100万
            if (orderNumCount >= maxPerMSECSize) {
                orderNumCount = 0L;
            }
            //组装订单号
            String countStr = maxPerMSECSize + orderNumCount + "";
            finOrderNum = StrUtil.nullToEmpty(pix) + nowLong + countStr.substring(1);
            orderNumCount++;
        }
        return finOrderNum;
    }

    /**
     * 从订单号中提取完整的日期时间
     * 订单号格式：前缀 + yyyyMMddHHmmssSSS + 计数器
     * 
     * @param orderSn 订单号
     * @return LocalDateTime 日期时间
     * @throws IllegalArgumentException 如果订单号格式错误
     * @author billow
     */
    public static LocalDateTime extractDateTimeFromOrderSn(String orderSn) {
        if (StrUtil.isBlank(orderSn)) {
            throw new IllegalArgumentException("订单号不能为空");
        }
        
        try {
            // 从订单号中提取数字部分
            String numberPart = orderSn.replaceAll("[^0-9]", "");

            int length = ORDER_TIME_FORMAT.length();
            // 检查数字部分长度是否足够包含时间信息
            if (numberPart.length() < length) {
                throw new IllegalArgumentException("订单号格式错误，数字部分长度不足：" + orderSn);
            }
            
            // 提取时间部分（前17位数字）
            String timePart = numberPart.substring(0, length);
            
            // 解析完整的日期时间
            return LocalDateTime.parse(timePart, DATETIME_FORMATTER);
            
        } catch (Exception e) {
            throw new IllegalArgumentException("从订单号[" + orderSn + "]提取日期时间失败：" + e.getMessage(), e);
        }
    }

//    public static void main(String[] args) {
//        // 测试多线程调用订单号生成工具
//        try {
//            for (int i = 0; i < 200; i++) {
//                System.out.println(OrderNumUtil.makeNum());
//                /*Thread t1 = new Thread(new Runnable() {
//                    public void run() {
//                        OrderNumUtil makeOrder = new OrderNumUtil();
//                        makeOrder.makeNum("a");
//                    }
//                }, "at" + i);
//                t1.start();
//
//                Thread t2 = new Thread(new Runnable() {
//                    public void run() {
//                        OrderNumUtil makeOrder = new OrderNumUtil();
//                        makeOrder.makeNum("b");
//                    }
//                }, "bt" + i);
//                t2.start();*/
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }

}

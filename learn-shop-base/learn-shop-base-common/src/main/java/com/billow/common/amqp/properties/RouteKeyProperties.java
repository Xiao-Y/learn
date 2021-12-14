package com.billow.common.amqp.properties;

import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-09-29 15:21
 */
@Data
public class RouteKeyProperties {

    // 日志收集
    private String logCollect;
    // 重置sql
    private String executeSql;
    // 运行定时任务
    private String runJobTest;

    // 发送邮件
    private String sendMail;
    private String sendMailDlx;
    private String sendMailTrt;

    // 刷新 es 缓存对列
    private String refreshEs;
}

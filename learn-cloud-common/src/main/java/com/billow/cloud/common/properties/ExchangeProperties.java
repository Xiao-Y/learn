package com.billow.cloud.common.properties;

import lombok.Data;

/**
 * 发送邮件交换机
 *
 * @author liuyongtao
 * @create 2019-09-29 15:47
 */
@Data
public class ExchangeProperties {

    // 日志收集
    private String logCollect;
    // 重置sql
    private String executeSql;
    // 运行定时任务
    private String runJobTest;

    // 发送邮件
    private String sendMail;

    // 商品系统交换机
    private String product;
    // 秒杀订单交换机
    private String secKillOrder;
}

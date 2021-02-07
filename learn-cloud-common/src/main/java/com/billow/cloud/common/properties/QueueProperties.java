package com.billow.cloud.common.properties;

import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-09-29 15:21
 */
@Data
public class QueueProperties {

    private String logCollect;
    private String executeSql;
    private String runJobTest;


    private String sendMail;
    private String sendMailDlx;
    private String sendMailTrt;

    // 刷新 es 缓存对列
    private String refreshEs;
}

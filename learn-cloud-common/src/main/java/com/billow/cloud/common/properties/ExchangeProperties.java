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

    private String executeSql;
    private String runJob;

    private String sendMail;
}

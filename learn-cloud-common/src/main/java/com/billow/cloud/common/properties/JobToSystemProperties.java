package com.billow.cloud.common.properties;

import lombok.Data;

/**
 * job 向 system 系统发出发送邮件请求
 *
 * @author liuyongtao
 * @create 2019-08-20 17:15
 */
@Data
public class JobToSystemProperties {

    private String sendMail;
}

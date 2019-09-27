package com.billow.job.pojo.ex;

import lombok.Data;

import java.io.Serializable;

/**
 * 发送邮件
 *
 * @author liuyongtao
 * @create 2019-08-20 17:23
 */
@Data
public class MailEx implements Serializable {

    public MailEx() {
    }

    //收件人邮箱，多个邮箱以“;”分隔
    private String toEmails;
    //邮件主题
    private String subject;
    // 邮件标识，唯一
    private String mailCode;
    // 邮件模板ID
    private Long mailTemplateId;
    // 日志id
    private Long logId;
}
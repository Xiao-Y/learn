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

    //自动任务id
    private Long jobId;
    // 任务名称
    private String jobName;
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
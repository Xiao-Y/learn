package com.billow.cloud.common.mqvo;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * 发送邮件
 *
 * @author liuyongtao
 * @create 2019-08-20 17:23
 */
@Data
public class MailVo implements Serializable {

    public MailVo() {
    }

    /**
     * 普通构造器
     *
     * @param toEmails 收件人邮箱，多个邮箱以“;”分隔
     * @param subject  邮件主题
     * @param content  邮件内容
     */
    public MailVo(String toEmails, String subject, String content) {
        this.toEmails = toEmails;
        this.subject = subject;
        this.content = content;
    }

    //收件人邮箱，多个邮箱以“;”分隔
    private String toEmails;
    //邮件主题
    private String subject;
    //邮件内容
    private String content;
    //附件
    private String[] attachFileNames;
    //邮件中的图片，为空时无图片。map中的key为图片ID，value为图片地址
    private Map<String, String> pictures;
    //邮件中的附件，为空时无附件。map中的key为附件ID，value为附件地址
    private Map<String, String> attachments;

    //发送人地址1个
    private String fromAddress;
    //发件人邮箱服务器
    private String emailHost;
    //发件人邮箱
    private String emailFrom;
    //发件人用户名
    private String emailUserName;
    //发件人密码
    private String emailPassword;
}
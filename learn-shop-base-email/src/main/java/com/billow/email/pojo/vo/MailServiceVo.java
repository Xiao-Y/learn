package com.billow.email.pojo.vo;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件服务模型
 *
 * @author liuyongtao
 * @create 2020-01-09 10:38
 */
@Data
public class MailServiceVo {

    // 邮件模板id,id与mailCode同时存在时，优先使用id
    private Long id;
    // 邮件模板code,id与mailCode同时存在时，优先使用id
    private String mailCode;
    // 邮件发送人。当指定发件人时，username和password不能为空时
    private String fromEmail;
    // 邮件发送人用户名。当指定发件人时，username和password不能为空时
    private String username;
    // 邮件发送人密码。当指定发件人时，username和password不能为空时
    private String password;
    // 邮件服务器
    private String host;
    // 邮件服务器端口
    private String port;
    // 邮件接收人
    private String toEmails;
    // 抄送人邮箱，多个邮箱以“;”分隔
    private String ccEmails;
    // 密抄送人邮箱，多个邮箱以“;”分隔
    private String bccEmails;
    // 邮件主题
    private String subject;
    // 邮件内容。指定内容时，id和mailCode要同时为空
    private String mailContent;
    // 附件，key-文件名，value=文件转 byte[]后的字符串。使用 FileUtils转换
    Map<String, String> attachments = new HashMap<>();
    // 是否html 邮件
    private boolean isHtml = false;
    // 邮件参数
    private Map<String, Object> parameter;

    public static MailServiceVo getInstance(String toEmails, String subject, String mailContent) {
        MailServiceVo serviceVo = new MailServiceVo();
        serviceVo.setToEmails(toEmails);
        serviceVo.setSubject(subject);
        serviceVo.setMailContent(mailContent);
        return serviceVo;
    }
}

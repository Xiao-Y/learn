package com.billow.email.pojo.vo;

import com.billow.email.pojo.po.MailTemplatePo;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashMap;
import java.util.Map;

/**
 * 邮件模板
 *
 * @author liuyongtao
 * @create 2019-08-21 9:23
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class MailTemplateVo extends MailTemplatePo {
    // 邮件内容
    private String mailContent;
    // 是否html 邮件
    private boolean isHtml;
    // 邮件发送人。当指定发件人时，username和password不能为空时
    private String fromEmail;
    // 邮件发送人用户名。当指定发件人时，username和password不能为空时
    private String username;
    // 邮件发送人密码。当指定发件人时，username和password不能为空时
    private String password;
    // 邮件服务器
    private String host;
    // 邮件服务器商品
    private String port;
    // 抄送人邮箱，多个邮箱以“;”分隔
    private String ccEmails;
    // 密抄送人邮箱，多个邮箱以“;”分隔
    private String bccEmails;
    // 附件，key-文件名，value=文件转 byte[]后的字符串。使用 FileUtils转换
    Map<String, String> attachments = new HashMap<>();
}

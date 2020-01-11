package com.billow.email.service.impl;

import com.billow.email.perproties.MailPerproties;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.EmailSender;
import com.billow.email.utils.ToolsUtils;
import lombok.extern.slf4j.Slf4j;

import javax.activation.DataHandler;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;
import java.util.Base64;
import java.util.Map;
import java.util.Properties;

/**
 * 邮件发送实现
 *
 * @author liuyongtao
 * @create 2020-01-10 8:32
 */
@Slf4j
public class EmailSenderImpl implements EmailSender {

    private MailPerproties mailPerproties;

    public EmailSenderImpl(MailPerproties mailPerproties) {
        this.mailPerproties = mailPerproties;
    }

    @Override
    public void send(MailTemplateVo mailTemplateVo) throws Exception {
        if (ToolsUtils.isEmpty(mailTemplateVo.getFromEmail()) || ToolsUtils.isEmpty(mailTemplateVo.getPassword())
                || ToolsUtils.isEmpty(mailTemplateVo.getUsername())) {
            mailTemplateVo.setFromEmail(mailPerproties.getFrom());
            mailTemplateVo.setUsername(mailPerproties.getUsername());
            mailTemplateVo.setPassword(mailPerproties.getPassword());
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getPort())) {
            mailTemplateVo.setPort(mailPerproties.getPort());
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getHost())) {
            mailTemplateVo.setHost(mailPerproties.getHost());
        }
        log.debug("MailServiceVo:{}", mailTemplateVo);
        // 校验数据
        this.checkData(mailTemplateVo);
        // 获取系统属性，主要用于设置邮件相关的参数。
        Properties properties = System.getProperties();
        // 设置邮件传输服务器，由于本次是发送邮件操作，所需我们需要配置smtp协议，按outlook官方同步邮件的要求，依次配置协议地址，端口号和加密方法
        properties.setProperty("mail.smtp.host", mailTemplateVo.getHost());
        // 邮件服务器端口
        properties.setProperty("mail.smtp.port", mailTemplateVo.getPort());
        // smtp host设为可信任
        properties.setProperty("mail.smtp.ssl.trust", mailTemplateVo.getHost());
        // 发送邮件协议名称
        properties.setProperty("mail.transport.protocol", mailPerproties.getProtocol());
        //用户验证并返回Session，开启用户验证，设置发送邮箱的账号密码。
        properties.setProperty("mail.smtp.auth", mailPerproties.getAuth().toString());
        // TLS加密
        properties.setProperty("mail.smtp.starttls.enable", mailPerproties.getStarttls().toString());
        // 开启 ssl
        properties.setProperty("mail.smtp.ssl.enable", mailPerproties.getSsl().toString());
        //开启debug调试，控制台会打印相关信息
        properties.setProperty("mail.debug", mailPerproties.getDebug().toString());
        // 需要切换发送人，所以不能使用 getDefaultInstance
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                //账号密码
                return new PasswordAuthentication(mailTemplateVo.getUsername(), mailTemplateVo.getPassword());
            }
        });
        //创建MimeMessage消息对象，消息头配置了收发邮箱的地址，消息体包含了邮件标题和邮件内容。
        MimeMessage message = new MimeMessage(session);
        // 设置发件人
        message.setFrom(new InternetAddress(mailTemplateVo.getFromEmail()));
        // 收件人，接收者类型：TO代表直接发送，CC代表抄送，BCC代表秘密抄送。
        message.addRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTemplateVo.getToEmails()));
        // 抄送人邮箱
        if (ToolsUtils.isNotEmpty(mailTemplateVo.getCcEmails())) {
            message.addRecipients(Message.RecipientType.CC, InternetAddress.parse(mailTemplateVo.getCcEmails()));
        }
        // 密抄送人邮箱
        if (ToolsUtils.isNotEmpty(mailTemplateVo.getBccEmails())) {
            message.addRecipients(Message.RecipientType.BCC, InternetAddress.parse(mailTemplateVo.getBccEmails()));
        }
        message.setSubject(mailTemplateVo.getSubject());
        // 1.创建复合消息体
        Multipart multipart = new MimeMultipart();
        // 2.添加附件
        Map<String, String> attachments = mailTemplateVo.getAttachments();
        if (ToolsUtils.isNotEmpty(attachments)) {
            for (Map.Entry<String, String> entry : attachments.entrySet()) {
                byte[] bytes = Base64.getDecoder().decode(entry.getValue());
                DataHandler dh = new DataHandler(new ByteArrayDataSource(bytes, "application/octet-stream"));
                BodyPart attachmentBodyPart = new MimeBodyPart();
                attachmentBodyPart.setDataHandler(dh);
                attachmentBodyPart.setFileName(entry.getKey());
                multipart.addBodyPart(attachmentBodyPart);
            }
        }
        // 3.添加文本内容
        MimeBodyPart textPart = new MimeBodyPart();
        if (mailTemplateVo.isHtml()) {
            textPart.setContent(mailTemplateVo.getMailContent(), "text/html; charset=utf-8");
        } else {
            textPart.setText(mailTemplateVo.getMailContent());
        }
        multipart.addBodyPart(textPart);
        // 4.绑定消息对象
        message.setContent(multipart);
        // 5.发送邮件
        Transport.send(message);
    }

    /**
     * 校验数据
     *
     * @param mailTemplateVo
     * @return void
     * @author LiuYongTao
     * @date 2020/1/9 17:11
     */
    private void checkData(MailTemplateVo mailTemplateVo) {
        String message = "";
        if (ToolsUtils.isEmpty(mailTemplateVo.getHost())) {
            message += "邮件服务器为空。请配置 custom.mail.host 或设置 MailServiceVo 中的 emailServer \n";
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getPort())) {
            message += "邮服务器端口为空。请配置 custom.mail.port 或设置 MailServiceVo 中的 emailPort \n";
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getUsername())) {
            message += "邮件发送人用户名为空。请配置 custom.mail.username 或设置 MailServiceVo 中的 username \n";
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getPassword())) {
            message += "邮件发送人邮箱密码为空。请配置 custom.mail.password 或设置 MailServiceVo 中的 password \n";
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getFromEmail())) {
            message += "邮件发送人为空。 请配置 custom.mail.from 或设置 MailServiceVo 中的 fromEmail \n";
        }
        String mailCode = mailTemplateVo.getMailCode();
        // 是否模板邮件
        boolean isTemplateMail = ToolsUtils.isNotEmpty(mailCode);
        if (ToolsUtils.isEmpty(mailTemplateVo.getToEmails())) {
            message += "邮件接收人为空。";
            if (isTemplateMail) {
                message += "请去 sys_mail_template 表中配置 mailCode：[" + mailCode + "] 的模板的 toEmails  \n";
            } else {
                message += "设置 MailServiceVo或者MailServiceVo 中的 toEmails \n";
            }
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getSubject())) {
            message += "邮件主题为空。 \n";
            if (isTemplateMail) {
                message += "请去 sys_mail_template 表中配置 mailCode：[" + mailCode + "] 的模板的 subject  \n";
            } else {
                message += "设置 MailServiceVo或者MailServiceVo 中的 subject \n";
            }
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getMailContent())) {
            message += "邮件内容为空。 \n";
            if (isTemplateMail) {
                message += "请去 sys_mail_template 表中配置 mailCode：[" + mailCode + "] 的模板的内容  \n";
            } else {
                message += "设置 MailServiceVo或者MailServiceVo 中的 mailContent \n";
            }
        }
        if (message.length() > 0) {
            throw new RuntimeException(message);
        }
    }
}
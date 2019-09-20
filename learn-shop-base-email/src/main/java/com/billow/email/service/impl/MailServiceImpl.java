package com.billow.email.service.impl;

import com.billow.email.constant.MailCst;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.MailService;
import com.billow.email.service.MailTemplateService;
import com.billow.email.utils.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private MailTemplateService mailTemplateService;

    @Async("emailExecutor")
    @Override
    public void sendTemplateMail(String fromEmail, String toEmails, String subject, String mailCode, Map<String, String> parameter) {
        this.sendTemplateMail(fromEmail, toEmails, subject, mailCode, parameter, null);
    }

    @Async("emailExecutor")
    @Override
    public void sendTemplateMail(String fromEmail, String toEmails, String subject, String mailCode, Map<String, String> parameter, String filePath) {
        try {
            log.info("开始发送模板邮件！");
            log.debug("fromEmail:{},toEmails:{},subject:{},mailCode:{},filePath:{}", fromEmail, toEmails, subject, mailCode, filePath);
            log.debug("parameter:{}", parameter);
            String fromEmailTemp = fromEmail;
            String toEmailsTemp = toEmails;
            String subjectTemp = subject;
            MailTemplateVo mailTemplateVo = mailTemplateService.genMailContent(mailCode, parameter);
            if (mailTemplateVo == null) {
                throw new RuntimeException("未查询到 " + mailCode + " 的邮件模板信息。请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板");
            }
            log.debug(mailTemplateVo.toString());
            // 优先使用指定的，如果为空，使用数据库中配置的
            if (ToolsUtils.isEmpty(toEmailsTemp)) {
                toEmailsTemp = mailTemplateVo.getToEmails();
                if (ToolsUtils.isEmpty(toEmailsTemp)) {
                    throw new RuntimeException("邮件接收人为空。请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板的 toEmails");
                }
            }
            if (ToolsUtils.isEmpty(subjectTemp)) {
                toEmailsTemp = mailTemplateVo.getSubject();
                if (ToolsUtils.isEmpty(toEmailsTemp)) {
                    throw new RuntimeException("邮件主题为空。请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板的 subject");
                }
            }

            String mailContent = mailTemplateVo.getMailContent();
            // 邮件类型，1-普通邮件，2-html邮件，3-带附件邮件,4-FreeMarker模板邮件,5-Thymeleaf模板邮件
            String mailType = mailTemplateVo.getMailType();
            switch (mailType) {
                case MailCst.SYS_FC_DATA_MAIL_COMMON: // 1-普通邮件
                    this.sendSimpleMail(fromEmailTemp, toEmailsTemp, subjectTemp, mailContent);
                    break;
                case MailCst.SYS_FC_DATA_MAIL_HTML: // 2-html邮件
                case MailCst.SYS_FC_DATA_MAIL_FM:   // 4-FreeMarker模板邮件
                case MailCst.SYS_FC_DATA_MAIL_THF:  // 5-Thymeleaf模板邮件
                    this.sendHtmlMail(fromEmailTemp, toEmailsTemp, subjectTemp, mailContent);
                    break;
                case MailCst.SYS_FC_DATA_MAIL_ATT: // 3-带附件邮件
                    if (ToolsUtils.isEmpty(filePath)) {
                        this.sendSimpleMail(fromEmailTemp, toEmailsTemp, subjectTemp, mailContent);
                    } else {
                        this.sendAttachmentsMail(fromEmailTemp, toEmailsTemp, subjectTemp, mailContent, filePath);
                    }
                    break;
                default:
                    // 1-普通邮件
                    this.sendSimpleMail(fromEmailTemp, toEmailsTemp, subjectTemp, mailContent);
            }
        } catch (Exception e) {
            log.error("模板邮件发送失败：{}", e);
        }
    }

    @Async("emailExecutor")
    @Override
    public void sendSimpleMail(String fromEmail, String toEmails, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        try {
            message.setFrom(fromEmail);
            message.setTo(toEmails);
            message.setSubject(subject);
            message.setText(content);
            mailSender.send(message);
            log.info("简单邮件发送成功！");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！{}", e.getMessage());
        }
    }

    @Async("emailExecutor")
    @Override
    public void sendHtmlMail(String fromEmail, String toEmails, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);   //true表示需要创建一个multipart message
            helper.setFrom(fromEmail);
            helper.setTo(toEmails);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(message);
            log.info("html邮件发送成功");
        } catch (Exception e) {
            log.error("发送html邮件时发生异常！{}", e.getMessage());
        }
    }

    @Async("emailExecutor")
    @Override
    public void sendAttachmentsMail(String fromEmail, String toEmails, String subject, String content, String filePath) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(fromEmail);
            helper.setTo(toEmails);
            helper.setSubject(subject);
            helper.setText(content, true);

            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
            mailSender.send(message);
            log.info("带附件的邮件已经发送。");
        } catch (MessagingException e) {
            log.error("发送带附件的邮件时发生异常！{}", e.getMessage());
        }
    }
}
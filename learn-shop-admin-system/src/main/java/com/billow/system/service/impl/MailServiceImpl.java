package com.billow.system.service.impl;

import com.billow.system.pojo.vo.MailTemplateVo;
import com.billow.system.properties.CustomProperties;
import com.billow.system.service.MailService;
import com.billow.system.service.MailTemplateService;
import com.billow.tools.constant.DictionaryType;
import com.billow.tools.utlis.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.Map;
import java.util.concurrent.ExecutorService;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Resource(name = "fxbDrawExecutor")
    private ExecutorService executorService;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CustomProperties customProperties;
    @Autowired
    private MailTemplateService mailTemplateService;

    @Override
    public void sendTemplateMail(String toEmails, String subject, String mailCode, Map<String, String> parameter) {
        this.sendTemplateMail(toEmails, subject, mailCode, parameter, null);
    }

    @Override
    public void sendTemplateMail(String toEmails, String subject, String mailCode, Map<String, String> parameter, String filePath) {
        executorService.execute(() -> {
            try {
                log.info("开始发送模板邮件！");
                MailTemplateVo mailTemplateVo = mailTemplateService.genMailContent(mailCode, parameter);
                String mailContent = mailTemplateVo.getMailContent();
                // 邮件类型，1-普通邮件，2-html邮件，3-带附件邮件
                String mailType = mailTemplateVo.getMailType();
                switch (mailType) {
                    case DictionaryType.SYS_FC_DATA_MAIL_COMMON: // 1-普通邮件
                        this.sendSimpleMail(toEmails, subject, mailContent);
                        break;
                    case DictionaryType.SYS_FC_DATA_MAIL_HTML: // 2-html邮件
                        this.sendHtmlMail(toEmails, subject, mailContent);
                        break;
                    case DictionaryType.SYS_FC_DATA_MAIL_ATT: // 3-带附件邮件
                        if (ToolsUtils.isEmpty(filePath)) {
                            this.sendSimpleMail(toEmails, subject, mailContent);
                        } else {
                            this.sendAttachmentsMail(toEmails, subject, mailContent, filePath);
                        }
                        break;
                    default:
                        // 1-普通邮件
                        this.sendSimpleMail(toEmails, subject, mailContent);
                }
                log.info("模板邮件发送成功！");
            } catch (Exception e) {
                log.error("模板邮件发送失败：{}", e);
            }
        });
    }

    @Override
    public void sendSimpleMail(String to, String subject, String content) {
        executorService.execute(() -> {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(customProperties.getMail().getFrom());
            message.setTo(to);
            message.setSubject(subject);
            message.setText(content);
            try {
                mailSender.send(message);
                log.info("简单邮件发送成功！");
            } catch (Exception e) {
                log.error("发送简单邮件时发生异常！{}", e.getMessage());
            }
        });
    }

    @Override
    public void sendHtmlMail(String to, String subject, String content) {
        executorService.execute(() -> {
            MimeMessage message = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);   //true表示需要创建一个multipart message
                helper.setFrom(customProperties.getMail().getFrom());
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(content, true);
                mailSender.send(message);
                log.info("html邮件发送成功");
            } catch (MessagingException e) {
                log.error("发送html邮件时发生异常！{}", e.getMessage());
            }
        });
    }

    @Override
    public void sendAttachmentsMail(String to, String subject, String content, String filePath) {
        executorService.execute(() -> {
            MimeMessage message = mailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(message, true);
                helper.setFrom(customProperties.getMail().getFrom());
                helper.setTo(to);
                helper.setSubject(subject);
                helper.setText(content, true);

                FileSystemResource file = new FileSystemResource(new File(filePath));
                String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
                helper.addAttachment(fileName, file);
                //helper.addAttachment("test"+fileName, file);
                mailSender.send(message);
                log.info("带附件的邮件已经发送。");
            } catch (MessagingException e) {
                log.error("发送带附件的邮件时发生异常！{}", e.getMessage());
            }
        });
    }
}
package com.billow.email.service.impl;

import com.billow.email.constant.MailCst;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.MailService;
import com.billow.email.service.MailTemplateService;
import com.billow.email.utils.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
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
    public void sendTemplateMail(String fromEmail, String toEmails, String subject, Long id, Map<String, String> parameter) {
        this.sendTemplateMail(fromEmail, toEmails, subject, id, parameter, null);
    }

    @Async("emailExecutor")
    @Override
    public void sendTemplateMail(String fromEmail, String toEmails, String subject, Long id, Map<String, String> parameter, String filePath) {
        MailTemplateVo mailTemplateVo = mailTemplateService.findByIdAndValidIndIsTrue(id);
        if (mailTemplateVo == null) {
            throw new RuntimeException("id:" + id + ",没有查询到模板信息");
        }
        this.sendTemplateMail(fromEmail, toEmails, subject, mailTemplateVo.getMailCode(), parameter, filePath);
    }

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
            // 获取邮件信息
            MailTemplateVo mailTemplateVo = mailTemplateService.genMailContent(mailCode, parameter);

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

            Boolean attachment = mailTemplateVo.getAttachment();
            if (attachment == null) {
                attachment = false;
            }
            if (attachment && ToolsUtils.isEmpty(filePath)) {
                throw new RuntimeException("邮件附件为空。请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板的 attachment");
            }

            // 获取邮件内容，发送邮件
            String mailContent = mailTemplateVo.getMailContent();
            String mailType = mailTemplateVo.getMailType();
            boolean isHteml = !MailCst.SYS_FC_DATA_MAIL_COMMON.equals(mailType);
            if (attachment) {
                this.sendAttachmentsMail(fromEmailTemp, toEmailsTemp, subjectTemp, mailContent, filePath, isHteml);
            } else {
                if (isHteml) {
                    this.sendHtmlMail(fromEmailTemp, toEmailsTemp, subjectTemp, mailContent);
                } else {
                    this.sendSimpleMail(fromEmailTemp, toEmailsTemp, subjectTemp, mailContent);
                }

            }
        } catch (Exception e) {
            log.error("模板邮件发送失败：{}", e.getMessage());
        }
    }

    @Async("emailExecutor")
    @Override
    public void sendSimpleMail(String fromEmail, String toEmails, String subject, String content) {
        try {
            this.sendMail(fromEmail, toEmails, subject, content, null, false);
            log.info("简单邮件发送成功！");
        } catch (Exception e) {
            log.error("发送简单邮件时发生异常！{}", e.getMessage());
        }
    }

    @Async("emailExecutor")
    @Override
    public void sendHtmlMail(String fromEmail, String toEmails, String subject, String content) {
        try {
            this.sendMail(fromEmail, toEmails, subject, content, null, true);
            log.info("html邮件发送成功");
        } catch (Exception e) {
            log.error("发送html邮件时发生异常！{}", e.getMessage());
        }
    }

    @Async("emailExecutor")
    @Override
    public void sendAttachmentsMail(String fromEmail, String toEmails, String subject, String content, String filePath, boolean isHtml) {
        MimeMessage message = mailSender.createMimeMessage();
        try {
            this.sendMail(fromEmail, toEmails, subject, content, filePath, isHtml);
            log.info("带附件的邮件已经发送。");
        } catch (Exception e) {
            log.error("发送带附件的邮件时发生异常！{}", e.getMessage());
        }
    }

    /**
     * 发送邮件
     *
     * @param fromEmail 发送人
     * @param toEmails  接收人
     * @param subject   主题
     * @param content   内容
     * @param filePath  附件路径
     * @param isHtml    是否html 邮件
     * @return void
     * @author LiuYongTao
     * @date 2019/9/24 10:02
     */
    private void sendMail(String fromEmail, String toEmails, String subject, String content, String filePath, boolean isHtml) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom(fromEmail);
        helper.setTo(toEmails);
        helper.setSubject(subject);
        helper.setText(content, isHtml);

        if (ToolsUtils.isNotEmpty(filePath)) {
            FileSystemResource file = new FileSystemResource(new File(filePath));
            String fileName = filePath.substring(filePath.lastIndexOf(File.separator));
            helper.addAttachment(fileName, file);
        }
        mailSender.send(message);
    }
}
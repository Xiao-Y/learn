package com.billow.email.service.impl;

import com.billow.email.constant.MailCst;
import com.billow.email.pojo.vo.MailServiceVo;
import com.billow.email.pojo.vo.MailTemplateVo;
import com.billow.email.service.EmailSender;
import com.billow.email.service.MailService;
import com.billow.email.service.MailTemplateService;
import com.billow.email.utils.ToolsUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Map;

@Slf4j
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private EmailSender mailSender;
    @Autowired
    private MailTemplateService mailTemplateService;

    @Async("emailExecutor")
    @Override
    public void sendTemplateMail(String toEmails, String subject, Long id, Map<String, Object> parameter) throws Exception {
        MailServiceVo mailServiceVo = MailServiceVo.getInstance(toEmails, subject, null);
        mailServiceVo.setId(id);
        mailServiceVo.setParameter(parameter);
        this.sendMail(mailServiceVo);
        log.info("模板邮件发送成功！");
    }

    @Async("emailExecutor")
    @Override
    public void sendTemplateMail(String toEmails, String subject, String mailCode, Map<String, Object> parameter) throws Exception {
        MailServiceVo mailServiceVo = MailServiceVo.getInstance(toEmails, subject, null);
        mailServiceVo.setMailCode(mailCode);
        mailServiceVo.setParameter(parameter);
        this.sendMail(mailServiceVo);
        log.info("模板邮件发送成功！");
    }

    @Async("emailExecutor")
    @Override
    public void sendSimpleMail(String toEmails, String subject, String content) throws Exception {
        MailServiceVo mailServiceVo = MailServiceVo.getInstance(toEmails, subject, content);
        this.sendMail(mailServiceVo);
        log.info("简单邮件发送成功！");
    }

    @Async("emailExecutor")
    @Override
    public void sendHtmlMail(String toEmails, String subject, String content) throws Exception {
        MailServiceVo mailServiceVo = MailServiceVo.getInstance(toEmails, subject, content);
        mailServiceVo.setHtml(true);
        this.sendMail(mailServiceVo);
        log.info("html邮件发送成功");
    }

    @Async("emailExecutor")
    @Override
    public void sendMail(MailServiceVo mailServiceVo) throws Exception {
        log.info("开始发送邮件！");
        log.debug("mailServiceVo:{}", mailServiceVo);
        // 检查数据
        MailTemplateVo mailTemplateVo = this.checkAndSettingData(mailServiceVo);
        log.debug("mailTemplateVo:{}", mailTemplateVo);
        mailSender.send(mailTemplateVo);
        log.info("结束发送邮件！id:{},mailCode:{}", mailTemplateVo.getId(), mailTemplateVo.getMailCode());
    }

    /**
     * 数据查询并设置数据
     *
     * @param mailServiceVo
     * @return com.billow.email.pojo.vo.MailTemplateVo
     * @author LiuYongTao
     * @date 2020/1/9 12:32
     */
    private MailTemplateVo checkAndSettingData(MailServiceVo mailServiceVo) throws Exception {
        log.debug("开始检查数据！");
        MailTemplateVo mailTemplateVo = new MailTemplateVo();
        // 获取邮件模板 code
        Long id = mailServiceVo.getId();
        String mailCode = mailServiceVo.getMailCode();
        if (id != null) {
            mailTemplateVo = mailTemplateService.findByIdAndValidIndIsTrue(id);
            if (mailTemplateVo == null) {
                throw new RuntimeException("id:" + id + ",没有查询到模板信息");
            } else {
                mailCode = mailTemplateVo.getMailCode();
            }
        }
        // 获取邮件信息
        if (ToolsUtils.isNotEmpty(mailCode)) {
            mailTemplateVo = mailTemplateService.genMailContent(mailCode, mailServiceVo.getParameter());
        } else {
            mailTemplateVo.setMailContent(mailServiceVo.getMailContent());
        }
        // 邮件服务器
        mailTemplateVo.setHost(mailServiceVo.getHost());
        // 邮件服务器端口
        mailTemplateVo.setPort(mailServiceVo.getPort());
        // 邮箱用户名
        mailTemplateVo.setUsername(mailServiceVo.getUsername());
        // 邮箱密码
        mailTemplateVo.setPassword(mailServiceVo.getPassword());
        // 发件人
        mailTemplateVo.setFromEmail(mailServiceVo.getFromEmail());
        // 抄送人邮箱，多个邮箱以“;”分隔
        mailTemplateVo.setCcEmails(mailServiceVo.getCcEmails());
        // 密抄送人邮箱，多个邮箱以“;”分隔
        mailTemplateVo.setBccEmails(mailServiceVo.getBccEmails());
        // 附件
        mailTemplateVo.setAttachments(mailServiceVo.getAttachments());
        // 是否发送的模板邮件
        boolean isTemplateMail = false;
        if (id != null || ToolsUtils.isNotEmpty(mailCode)) {
            isTemplateMail = true;
        }
        // 收件人，优先使用指定的，如果为空，使用数据库中配置的
        String toEmailsTemp = mailServiceVo.getToEmails();
        if (ToolsUtils.isNotEmpty(toEmailsTemp)) {
            mailTemplateVo.setToEmails(toEmailsTemp);
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getToEmails())) {
            String message = "邮件接收人为空。";
            if (isTemplateMail) {
                message += "请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板的 toEmails";
            } else {
                message += "请设置 MailServiceVo 中的 toEmails";
            }
            throw new RuntimeException(message);
        }
        // 邮件主题，优先使用指定的，如果为空，使用数据库中配置的
        String subjectTemp = mailServiceVo.getSubject();
        if (ToolsUtils.isNotEmpty(subjectTemp)) {
            mailTemplateVo.setSubject(subjectTemp);
        }
        if (ToolsUtils.isEmpty(mailTemplateVo.getSubject())) {
            String message = "邮件主题为空。";
            if (isTemplateMail) {
                message += "请去 sys_mail_template 表中配置 mailCode：" + mailCode + " 的模板的 subject";
            } else {
                message += "请设置 MailServiceVo 中的 subject";
            }
            throw new RuntimeException(message);
        }
        // 是否html 邮件
        String mailType = mailTemplateVo.getMailType();
        boolean isHtml = !MailCst.SYS_FC_DATA_MAIL_COMMON.equals(mailType);
        if (ToolsUtils.isEmpty(mailType)) {
            isHtml = mailServiceVo.isHtml();
        }
        mailTemplateVo.setHtml(isHtml);
        log.debug("结束检查数据！");
        return mailTemplateVo;
    }
}
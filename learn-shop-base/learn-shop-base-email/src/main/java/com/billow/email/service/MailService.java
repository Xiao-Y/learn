package com.billow.email.service;

import com.billow.email.pojo.vo.MailServiceVo;

import java.util.Map;

/**
 * 邮件服务类
 *
 * @author LiuYongTao
 * @date 2019/8/20 19:31
 */
public interface MailService {

    /**
     * 发送模板邮件
     *
     * @param toEmails  接收人
     * @param subject   主题
     * @param id        邮件id
     * @param parameter 邮件参数
     */
    void sendTemplateMail(String toEmails, String subject, Long id, Map<String, Object> parameter) throws Exception;

    /**
     * 发送模板邮件
     *
     * @param toEmails  接收人
     * @param subject   主题
     * @param mailCode  邮件code
     * @param parameter 邮件参数
     */
    void sendTemplateMail(String toEmails, String subject, String mailCode, Map<String, Object> parameter) throws Exception;

    /**
     * 发送简单邮件
     *
     * @param toEmails  接收人
     * @param subject   主题
     * @param content   内容
     * @return void
     * @author LiuYongTao
     * @date 2019/8/20 19:31
     */
    void sendSimpleMail(String toEmails, String subject, String content) throws Exception;

    /**
     * 发送HTML邮件
     *
     * @param toEmails  接收人
     * @param subject   主题
     * @param content   内容
     * @return void
     * @author LiuYongTao
     * @date 2019/8/20 19:32
     */
    void sendHtmlMail(String toEmails, String subject, String content) throws Exception;

    /**
     * 发送邮件
     *
     * @param mailServiceVo
     * @return void
     * @author LiuYongTao
     * @date 2020/1/9 14:30
     */
    void sendMail(MailServiceVo mailServiceVo) throws Exception;
}
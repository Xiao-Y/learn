package com.billow.email.service;

import com.billow.email.pojo.vo.MailTemplateVo;

/**
 * 邮件发送
 *
 * @author LiuYongTao
 * @date 2020/1/8 11:23
 */
public interface EmailSender {

    /**
     * 发送邮件
     *
     * @param mailTemplateVo
     * @return void
     * @author LiuYongTao
     * @date 2020/1/8 11:25
     */
    void send(MailTemplateVo mailTemplateVo) throws Exception;

}

package com.billow.system.service;

/**
 * 邮件服务类
 *
 * @author LiuYongTao
 * @date 2019/8/20 19:31
 */
public interface MailService {
    /*
     * 发送简单邮件
     *
     * @param to 接收人
     * @param subject 主题
     * @param content 内容
     * @return void
     * @author LiuYongTao
     * @date 2019/8/20 19:31
     */
    void sendSimpleMail(String to, String subject, String content);

    /**
     * 发送HTML邮件
     *
     * @param to      接收人
     * @param subject 主题
     * @param content 内容
     * @return void
     * @author LiuYongTao
     * @date 2019/8/20 19:32
     */
    void sendHtmlMail(String to, String subject, String content);

    /**
     * 发送含有附件的邮件
     *
     * @param to       接收人
     * @param subject  主题
     * @param content  内容
     * @param filePath 附件路径
     * @return void
     * @author LiuYongTao
     * @date 2019/8/20 19:32
     */
    void sendAttachmentsMail(String to, String subject, String content, String filePath);
}
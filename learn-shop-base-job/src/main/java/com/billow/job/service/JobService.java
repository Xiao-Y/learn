package com.billow.job.service;

import com.billow.job.pojo.ex.MailEx;

/**
 * 自动任务发送邮件接口
 *
 * @author LiuYongTao
 * @date 2019/9/27 16:54
 */
public interface JobService {

    /**
     * 自动任务发送邮件接口
     *
     * @param mailEx
     * @return void
     * @author LiuYongTao
     * @date 2019/9/27 16:53
     */
    void sendMail(MailEx mailEx);
}

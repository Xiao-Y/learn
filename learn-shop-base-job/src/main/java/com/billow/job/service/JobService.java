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

    /**
     * 发送 mq 请求
     *
     * @param jsonParam
     * @return void
     * @author LiuYongTao
     * @date 2019/12/6 15:09
     */
    void sendMQ(String jsonParam);

    /**
     * http 请求的 post 方法
     *
     * @param url
     * @param jsonParam
     * @return void
     * @author LiuYongTao
     * @date 2019/12/6 15:07
     */
    void post(String url, String jsonParam);
}

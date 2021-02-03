package com.billow.job.service;

import com.billow.job.pojo.ex.MailEx;

import java.util.Map;

/**
 * 自动任务自动任务服务接口
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
    void sendMail(MailEx mailEx) throws Exception;

    /**
     * 发送 mq 请求
     *
     * @param routingKey mq 的路由 key
     * @param param      携带的参数
     * @return void
     * @author LiuYongTao
     * @date 2019/12/6 15:09
     */
    void sendMQ(String routingKey, String param);

    /**
     * http 请求的 get 方法
     *
     * @param url
     * @return void
     * @author LiuYongTao
     * @date 2019/12/6 15:07
     */
    void httpGet(String url);

    /**
     * Feign 形式的 post 请示
     *
     * @param url
     * @param body
     * @author xiaoy
     * @since 2021/1/24 14:52
     */
    void httpFeign(String url, Map<String, String> body);
}

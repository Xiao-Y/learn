package com.billow.job.service.impl;

import com.billow.job.pojo.ex.MailEx;
import com.billow.job.service.JobService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 默认实现的自动任务服务类
 *
 * @author liuyongtao
 * @create 2019-09-26 15:55
 */
public class JobServiceImpl implements JobService {

    private Logger logger = LoggerFactory.getLogger(getClass());
    String message = "请配置 JobService 接口的实现.bean name 为 jobServiceImpl";

    @Override
    public void sendMail(MailEx mailEx) {
        logger.error(message);
        throw new RuntimeException(message);
    }

    @Override
    public void sendMQ(String routingKey, String param) {
        logger.error(message);
        throw new RuntimeException(message);
    }

    @Override
    public void httpGet(String url) {
        logger.error(message);
        throw new RuntimeException(message);
    }
}

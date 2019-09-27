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
public class DefJobServiceImpl implements JobService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void sendMail(MailEx mailEx) {
        logger.warn("请配置 JobService 接口的实现.");
        throw new RuntimeException("请配置 JobService 接口的实现");
    }
}

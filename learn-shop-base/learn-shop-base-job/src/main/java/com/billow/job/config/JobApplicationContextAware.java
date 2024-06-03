package com.billow.job.config;

import com.billow.job.util.JobContextUtil;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * 收集上下文
 *
 * @author 千面
 * @date 2024/6/1 19:39
 */
public class JobApplicationContextAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        JobContextUtil.setApplicationContext(applicationContext);
    }
}
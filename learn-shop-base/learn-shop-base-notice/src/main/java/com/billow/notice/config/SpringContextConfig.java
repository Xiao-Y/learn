package com.billow.notice.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

/**
 * 获取 ApplicationContext
 *
 * @author 千面
 * @date 2021/12/15 8:33
 */
@Configuration
public class SpringContextConfig implements ApplicationContextAware
{

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        SpringContextUtil.setApplicationContext(applicationContext);
    }
}

package com.billow.email.config;

import com.billow.email.dao.MailTemplateDao;
import com.billow.email.dao.impl.MailTemplateDaoImpl;
import com.billow.email.perproties.MailPerproties;
import com.billow.email.service.EmailSender;
import com.billow.email.service.MailContentBuild;
import com.billow.email.service.MailService;
import com.billow.email.service.MailTemplateService;
import com.billow.email.service.build.BaseContentBuild;
import com.billow.email.service.build.FmMailContentBuild;
import com.billow.email.service.build.HtmlMailContentBuild;
import com.billow.email.service.build.ThfMailContentBuild;
import com.billow.email.service.impl.EmailSenderImpl;
import com.billow.email.service.impl.MailServiceImpl;
import com.billow.email.service.impl.MailTemplateServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

@Slf4j
@Configuration
@EnableAsync
@EnableConfigurationProperties(MailPerproties.class) // 用于@Autowired注入
public class EmailBeanConfig {

    // 线程池长期维持的线程数
    private static final int corePoolSize = 8;
    // 线程数的上限
    private static final int maximumPoolSize = 10;
    // 任务的排队队列
    private static final int capacity = 512;

    /**
     * 邮件线程池配置
     *
     * @return java.util.concurrent.ExecutorService
     * @author billow
     * @date 2019/9/19 22:11
     */
    @Bean("emailExecutor")
    @ConditionalOnMissingBean(name = "emailExecutor")
    public ExecutorService emailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maximumPoolSize);
        executor.setQueueCapacity(capacity);
        executor.setThreadNamePrefix("email-service-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor.getThreadPoolExecutor();
    }

    /**
     * spring 管理 邮件发送
     *
     * @return com.billow.email.service.EmailSender
     * @author LiuYongTao
     * @date 2020/1/10 8:27
     */
    @Bean
    @ConditionalOnMissingBean(EmailSender.class)
    public EmailSender emailSenderDefault(MailPerproties mailPerproties) {
        return new EmailSenderImpl(mailPerproties);
    }

    /**
     * 邮件模板保存数据库的方法
     *
     * @return com.billow.email.dao.MailTemplateDao
     * @author LiuYongTao
     * @date 2020/1/10 8:36
     */
    @Bean
    @ConditionalOnMissingBean(MailTemplateDao.class)
    public MailTemplateDao mailTemplateDaoDefault() {
        return new MailTemplateDaoImpl();
    }

    /**
     * 邮件服务类
     *
     * @return {@link MailService}
     * @author liuyongtao
     * @since 2021-2-3 11:45
     */
    @Bean
    public MailService mailService() {
        return new MailServiceImpl();
    }

    /**
     * 基类模板邮件构建器
     *
     * @return {@link BaseContentBuild}
     * @author liuyongtao
     * @since 2021-2-3 11:45
     */
    @Bean
    public BaseContentBuild baseContentBuild() {
        return new BaseContentBuild();
    }

    /**
     * FreeMarker模板邮件构建器
     *
     * @return {@link FmMailContentBuild}
     * @author liuyongtao
     * @since 2021-2-3 11:45
     */
    @Bean
    public MailContentBuild fmMailContentBuild() {
        return new FmMailContentBuild();
    }

    /**
     * 1-普通邮件,2-html邮件 内容构建
     *
     * @return {@link HtmlMailContentBuild}
     * @author liuyongtao
     * @since 2021-2-3 11:45
     */
    @Bean
    public MailContentBuild htmlMailContentBuild() {
        return new HtmlMailContentBuild();
    }

    /**
     * Thymeleaf邮件模板构建器
     *
     * @return {@link ThfMailContentBuild}
     * @author liuyongtao
     * @since 2021-2-3 11:45
     */
    @Bean
    public MailContentBuild thfMailContentBuild() {
        return new ThfMailContentBuild();
    }

    /**
     * 邮件服务器类
     *
     * @return {@link MailTemplateServiceImpl}
     * @author liuyongtao
     * @since 2021-2-3 11:45
     */
    @Bean
    public MailTemplateService mailTemplateService() {
        return new MailTemplateServiceImpl();
    }
}
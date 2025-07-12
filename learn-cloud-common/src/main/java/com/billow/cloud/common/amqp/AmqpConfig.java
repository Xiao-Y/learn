package com.billow.cloud.common.amqp;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmqpConfig {
    /**
     * 日志收集
     *
     * @param
     * @return AmqpYml
     * @author 千面
     * @date 2021/12/18 11:17
     */
    @Bean
    @ConfigurationProperties(prefix = "notice.mq.mq-collect.log-collect")
    public AmqpYml logCollect() {
        return new AmqpYml();
    }

    /**
     * 运行自动任务
     *
     * @param
     * @return AmqpYml
     * @author 千面
     * @date 2021/12/18 11:17
     */
    @Bean
    @ConfigurationProperties(prefix = "notice.mq.mq-collect.run-job-test")
    public AmqpYml runJobTest() {
        return new AmqpYml();
    }

    /**
     * 执行sql 配置
     *
     * @param
     * @return AmqpYml
     * @author 千面
     * @date 2021/12/18 11:17
     */
    @Bean
    @ConfigurationProperties(prefix = "notice.mq.mq-collect.execute-sql")
    public AmqpYml executeSql() {
        return new AmqpYml();
    }

    /**
     * 刷新 es 缓存 配置
     *
     * @param
     * @return AmqpYml
     * @author 千面
     * @date 2021/12/18 11:18
     */
    @Bean
    @ConfigurationProperties(prefix = "notice.mq.mq-collect.refresh-es")
    public AmqpYml refreshEs() {
        return new AmqpYml();
    }

    /**
     * 秒杀订单发送订单系统 配置
     *
     * @param
     * @return AmqpYml
     * @author 千面
     * @date 2021/12/18 11:17
     */
    @Bean
    @ConfigurationProperties(prefix = "notice.mq.mq-collect.sec-kill-to-core-order")
    public AmqpYml secKillToCoreOrder() {
        return new AmqpYml();
    }

    /**
     * 秒杀订单发送system系统 配置
     *
     * @param
     * @return AmqpYml
     * @author 千面
     * @date 2021/12/18 11:18
     */
    @Bean
    @ConfigurationProperties(prefix = "notice.mq.mq-collect.sec-kill-to-admin-system")
    public AmqpYml secKillToAdminSystem() {
        return new AmqpYml();
    }

    /**
     * 发送邮件 配置
     *
     * @param
     * @return AmqpYml
     * @author 千面
     * @date 2021/12/18 11:18
     */
    @Bean
    @ConfigurationProperties(prefix = "notice.mq.mq-collect.send-mail")
    public AmqpYml sendMail() {
        return new AmqpYml();
    }
}

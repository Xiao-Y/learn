package com.billow.notice.amqp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * mq 消息通知配置文件
 *
 * @author xiaoy
 * @since 2021/12/14 22:37
 */
@Data
@ConfigurationProperties(prefix = "notice.mq")
public class NoticeMqYml
{

    /**
     * mq 的队列、路由、交换机配置
     *
     * @author xiaoy
     * @since 2021/12/14 22:42
     */
    private Map<String, MqSetting> mqCollect = new HashMap<>();
}

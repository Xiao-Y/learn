package com.billow.notice.amqp.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

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
     * 中间件类型
     *
     * @author xiaoy
     * @since 2021/12/14 22:42
     */
    private String type;

    /**
     * 服务器地址
     *
     * @author xiaoy
     * @since 2021/12/14 22:42
     */
    private String host;

    /**
     * 服务器端口
     *
     * @author xiaoy
     * @since 2021/12/14 22:42
     */
    private int port;

    /**
     * 用户名和密码
     *
     * @author xiaoy
     * @since 2021/12/14 22:42
     */
    private String username;

    /**
     * 用户名和密码
     *
     * @author xiaoy
     * @since 2021/12/14 22:42
     */
    private String password;

    /**
     * 虚拟主机
     *
     * @author xiaoy
     * @since 2021/12/14 22:42
     */
    private String virtualHost;

    /**
     * mq 的队列、路由、交换机配置
     *
     * @author xiaoy
     * @since 2021/12/14 22:42
     */
    private List<MqSetting> mqCollect = new ArrayList<>();
}

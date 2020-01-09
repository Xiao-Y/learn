package com.billow.email.perproties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 邮件相关
 *
 * @author liuyongtao
 * @create 2019-08-20 19:39
 */
@Data
@Component
@ConfigurationProperties(prefix = "custom.mail")
public class MailPerproties {
    // 发送邮件的地址
    private String from;
    // 服务器
    private String host;
    // 端口
    private String port;
    // 邮箱用户名
    private String username;
    // 邮箱密码
    private String password;
}

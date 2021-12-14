package com.billow.notice.ding;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 钉钉机器在人配置
 *
 * @author 千面
 * @date 2021/12/14 10:34
 */
@Data
@ConfigurationProperties(prefix = "notice.ding.robot")
public class RobotProperties
{
    private String webhook;

    private String robotKey;
}

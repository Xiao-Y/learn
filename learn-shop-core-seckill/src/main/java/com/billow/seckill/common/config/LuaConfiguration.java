package com.billow.seckill.common.config;

import com.billow.seckill.common.enums.SeckillStatEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class LuaConfiguration {

    /**
     * 秒杀 lua 脚本配置
     *
     * @return {@link DefaultRedisScript< Long>} {@link SeckillStatEnum}
     * @author xiaoy
     * @since 2021/1/23 12:06
     */
//    @Bean
    public DefaultRedisScript<Long> seckillScript() {
        DefaultRedisScript<Long> redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource("script/seckill.lua")));
        redisScript.setResultType(Long.class);
        return redisScript;
    }
}
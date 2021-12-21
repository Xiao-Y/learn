package com.billow.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * redis 配置文件
 *
 * @author LiuYongTao
 * @date 2019/7/29 9:13
 */
@Configuration
public class RedisConfig {

    private static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    @Autowired
    private RedisProperties redisProperties;

    @Bean
    @Primary
    public LettuceConnectionFactory lettuceConnectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setDatabase(redisProperties.getDatabase());
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setPort(redisProperties.getPort());
        redisStandaloneConfiguration.setPassword(RedisPassword.of(redisProperties.getPassword()));

        LettuceClientConfiguration.LettuceClientConfigurationBuilder lettuceClientConfigurationBuilder = LettuceClientConfiguration.builder();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(redisStandaloneConfiguration,
                lettuceClientConfigurationBuilder.build());
        logger.info("LettuceConnectionFactory bean init success.");
        return factory;
    }


    @Bean
    @Primary
    public RedisTemplate<String, String> customRedisTemplate(@Qualifier("lettuceConnectionFactory") LettuceConnectionFactory connectionFactory) {
        RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
        initDomainRedisTemplate(redisTemplate, connectionFactory);
        return redisTemplate;
    }

    /**
     * 设置数据存入 redis 的序列化方式
     *
     * @param template
     * @param factory
     */
    private void initDomainRedisTemplate(RedisTemplate<String, String> template, LettuceConnectionFactory factory) {
        // 定义 key 的序列化方式为 string
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        //string 的序列化
        template.setKeySerializer(stringRedisSerializer);
        //hash 的序列化
        template.setHashKeySerializer(stringRedisSerializer);

        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(String.class);
        // value 的序列化
        template.setValueSerializer(fastJsonRedisSerializer);
        // hash value 的序列化
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.setDefaultSerializer(stringRedisSerializer);
        // 开启事务
//        template.setEnableTransactionSupport(true);
//        template.afterPropertiesSet();
        template.setConnectionFactory(factory);

    }
}

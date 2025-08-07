//package com.billow.redis.config;
//
//import io.netty.channel.nio.NioEventLoopGroup;
//import lombok.Data;
//import org.redisson.Redisson;
//import org.redisson.api.RedissonClient;
//import org.redisson.config.Config;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.util.StringUtils;
//
//@Data
//@Configuration
//@ConfigurationProperties(prefix = "spring.redis.redisson")
//public class RedissonConfig {
//
//    @Autowired
//    private RedisProperties redisProperties;
//
//    private int connectionMinimumIdleSize = 10;
//    private int idleConnectionTimeout = 10000;
//    private int connectTimeout = 10000;
//    private int timeout = 3000;
//    private int retryAttempts = 3;
//    private int retryInterval = 1500;
//    private int subscriptionsPerConnection = 5;
//    private String clientName = null;
//    private int subscriptionConnectionMinimumIdleSize = 1;
//    private int subscriptionConnectionPoolSize = 50;
//    private int connectionPoolSize = 64;
//    private int database = 0;
//    private int dnsMonitoringInterval = 5000;
//    private int thread; // 当前处理核数量 * 2
//
//
//    @Bean(destroyMethod = "shutdown")
//    public RedissonClient redisson() throws Exception {
//        Config config = new Config();
//        String password = redisProperties.getPassword();
//        config.useSingleServer().setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
//                .setConnectionMinimumIdleSize(connectionMinimumIdleSize)
//                .setConnectionPoolSize(connectionPoolSize)
//                .setDatabase(database)
//                .setDnsMonitoringInterval(dnsMonitoringInterval)
//                .setSubscriptionConnectionMinimumIdleSize(subscriptionConnectionMinimumIdleSize)
//                .setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize)
//                .setSubscriptionsPerConnection(subscriptionsPerConnection)
//                .setClientName(clientName)
//                .setRetryAttempts(retryAttempts)
//                .setRetryInterval(retryInterval)
//                .setTimeout(timeout)
//                .setConnectTimeout(connectTimeout)
//                .setIdleConnectionTimeout(idleConnectionTimeout)
//                .setPassword(StringUtils.isEmpty(password) ? null : password);
//        config.setThreads(thread);
//        config.setEventLoopGroup(new NioEventLoopGroup());
//        return Redisson.create(config);
//    }
//}
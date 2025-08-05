package com.billow.gateway.init;

import com.alibaba.fastjson.JSON;
import com.billow.redis.util.RedisUtils;
import com.billow.tools.constant.RedisCst;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * 初始化数据
 *
 * @author xiaoy
 * @since 2021/1/27 21:26
 */
@Slf4j
@Component
public class InitData implements ApplicationRunner {

    @Autowired
    private GatewayProperties gatewayProperties;

    @Resource
    private RedisUtils redisUtils;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        this.initRouteInfo();
    }

    /**
     * 加载路由信息到缓存
     *
     * @author xiaoy
     * @since 2021/1/27 22:02
     */
    private void initRouteInfo() {
        log.info("== 初始化路由信息-start ========");
        Map<String, String> collect = gatewayProperties.getRoutes().stream()
                .filter(f -> f.getUri() != null && StringUtils.isNotEmpty(f.getUri().getHost()))
                .collect(Collectors.toMap(RouteDefinition::getId,
                        routeDefinition -> routeDefinition.getUri().getHost(),
                        (v1, v2) -> v2));
        log.info("路由信息:{}", JSON.toJSONString(collect));
        redisUtils.setHash(RedisCst.COMM_ROUTE_INFO, collect);
        log.info("== 初始化路由信息-end ========\n");
    }
}

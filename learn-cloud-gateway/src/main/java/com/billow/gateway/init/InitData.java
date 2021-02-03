package com.billow.gateway.init;

import com.billow.tools.constant.RedisCst;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
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
    private RedisTemplate<String, String> redisTemplate;

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
        log.info("== start load route info ========");
        Map<String, String> collect = gatewayProperties.getRoutes().stream()
                .filter(f -> f.getUri() != null && StringUtils.isNotEmpty(f.getUri().getHost()))
                .collect(Collectors.toMap(RouteDefinition::getId,
                        routeDefinition -> routeDefinition.getUri().getHost(),
                        (v1, v2) -> v2));
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        opsForHash.putAll(RedisCst.COMM_ROUTE_INFO, collect);
        log.info("== end load route info ========\n");
    }
}

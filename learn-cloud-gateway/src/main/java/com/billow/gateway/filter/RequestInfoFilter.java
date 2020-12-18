package com.billow.gateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.Set;

/**
 * 请求信息过滤器
 *
 * @author liuyongtao
 * @create 2018-01-29 9:47
 */
@Component
public class RequestInfoFilter implements GlobalFilter, Ordered {

    private static Logger log = LoggerFactory.getLogger(RequestInfoFilter.class);

    protected String getRemoteHost(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String ip = headers.getFirst("x-forwarded-for");
        if (ip != null && ip.length() != 0 && !"unknown".equalsIgnoreCase(ip)) {
            // 多次反向代理后会有多个ip值，第一个ip才是真实ip
            if (ip.indexOf(",") != -1) {
                ip = ip.split(",")[0];
            }
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("Proxy-Client-IP");
            log.debug("Proxy-Client-IP ip:{}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("WL-Proxy-Client-IP");
            log.debug("WL-Proxy-Client-IP ip:{}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_CLIENT_IP");
            log.debug("HTTP_CLIENT_IP ip:{}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("HTTP_X_FORWARDED_FOR");
            log.debug("HTTP_X_FORWARDED_FOR ip:{}", ip);
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = headers.getFirst("X-Real-IP");
            log.debug("X-Real-IP ip:{}", ip);
        }
        log.debug("当前访问IP为：{}", ip);
        return ip;
    }

    protected String getClientType(ServerHttpRequest request) {
        HttpHeaders headers = request.getHeaders();
        String clientType = headers.getFirst("User-Agent");
        if (clientType == null || clientType.length() == 0 || "unknown".equalsIgnoreCase(clientType)) {
            clientType = "";
        }
        log.debug("当前访问客户端类型为：{}", clientType);
        return clientType;
    }


    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        return chain.filter(exchange).then(
                Mono.fromRunnable(() -> {
                    ServerHttpRequest request = exchange.getRequest();
//                    this.getRemoteHost(request);
//                    this.getClientType(request);
                    Set<URI> uris = exchange.getAttributeOrDefault(ServerWebExchangeUtils.GATEWAY_ORIGINAL_REQUEST_URL_ATTR, Collections.emptySet());
                    String originalUri = (uris.isEmpty()) ? "Unknown" : uris.iterator().next().toString();
                    Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
                    URI routeUri = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
                    log.info("\n 路由ID：{}，执行方法：{}", route.getId(), request.getMethod());
                    log.info("\r 请求URL--> {}", originalUri);
                    log.info("\r 路由到 --> {}", routeUri);
                })
        );
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE + 10;
    }
}
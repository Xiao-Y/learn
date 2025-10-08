package com.billow.gateway.filter;

import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.reactivestreams.Publisher;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 处理服务 /v3/api-docs接口返回的数据，在servers里添加可以通过网关直接访问的地址
 */
@Slf4j
@Component
public class DocServiceUrlModifyGatewayFilter implements GlobalFilter, Ordered {
    @Autowired
    private SpringDocConfigProperties springDocConfigProperties;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //直接用配置类的值，默认值是 /v3/api-docs
        String apiPath = springDocConfigProperties.getApiDocs().getPath();
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        URI uri = request.getURI();

        //非正常状态跳过
        if (response.getStatusCode().value() != HttpStatus.OK.value()) {
            return chain.filter(exchange);
        }

        //非springdoc文档不拦截
        if (!(StringUtils.isNotBlank(uri.getPath()) && uri.getPath().endsWith(apiPath))) {
            return chain.filter(exchange);
        }

        String uriString = uri.toString();
        String gatewayUrl = uriString.substring(0, uriString.lastIndexOf(apiPath));
        DataBufferFactory bufferFactory = response.bufferFactory();


        ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(response) {
            @Override
            public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
                //不处理
                if (!(body instanceof Flux)) {
                    return super.writeWith(body);
                }

                //处理SpringDoc-OpenAPI
                Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;
                return super.writeWith(fluxBody.buffer().map(dataBuffers -> {
                    DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
                    DataBuffer join = dataBufferFactory.join(dataBuffers);
                    byte[] content = new byte[join.readableByteCount()];
                    join.read(content);
                    DataBufferUtils.release(join);
                    try {
                        // 流转为字符串
                        String responseData = new String(content, StandardCharsets.UTF_8);
                        Map<String, Object> map = JSONUtil.toBean(responseData, Map.class);
                        //处理返回的数据
                        Object serversObject = map.get("servers");
                        if (null != serversObject) {
                            List<Map<String, Object>> servers = (List<Map<String, Object>>) serversObject;
                            Map<String, Object> gatewayServer = new HashMap<>();
                            //网关地址
                            gatewayServer.put("url", gatewayUrl);
                            gatewayServer.put("description", "Gateway server url");
                            //添加到第1个
                            servers.add(0, gatewayServer);
                            map.put("servers", servers);
                            responseData = JSONUtil.toJsonStr(map);
                            byte[] uppedContent = responseData.getBytes(StandardCharsets.UTF_8);
                            response.getHeaders().setContentLength(uppedContent.length);
                            return bufferFactory.wrap(uppedContent);
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }

                    return bufferFactory.wrap(content);
                }));
            }
        };
        // replace response with decorator
        return chain.filter(exchange.mutate().response(decoratedResponse).build());
    }

    @Override
    public int getOrder() {
        return -2;
    }
}

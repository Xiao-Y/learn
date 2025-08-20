package com.billow.gateway.swagger2;

import jakarta.annotation.PostConstruct;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Configuration
public class OpenApiProvider {

    public static final String API_URI = "/v3/api-docs";

    @Value("${swagger.service.name}")
    private String serviceName;

    @Autowired
    private RouteDefinitionLocator routeDefinitionLocator;

    @Autowired
    private SwaggerUiConfigProperties swaggerUiConfigProperties;

    // 初始化和定期更新API文档URL
    @PostConstruct
    public void updateApiUrls() {
        Map<String, SwaggerUiConfigProperties.SwaggerUrl> urlMap = new HashMap<>();

        // 网关
        urlMap.put(serviceName, new SwaggerUiConfigProperties.SwaggerUrl(
                serviceName,
                API_URI,
                ""));

        // 获取所有路由定义
        List<RouteDefinition> definitions = routeDefinitionLocator
                .getRouteDefinitions()
                .collectList()
                .block();
        if (definitions != null) {
            for (RouteDefinition routeDefinition : definitions) {
                String serviceName = routeDefinition.getUri().getHost();
                String path = routeDefinition.getId();
                urlMap.put(serviceName, genSwaggerUrl(serviceName, path));
            }
        }
        swaggerUiConfigProperties.setUrls(new HashSet<>(urlMap.values()));
    }

    // 简化版：实际实现需要访问Gateway的路由定义器
    private SwaggerUiConfigProperties.SwaggerUrl genSwaggerUrl(String serviceName, String path) {
        return new SwaggerUiConfigProperties.SwaggerUrl(
                serviceName,
                "/" + path + API_URI,
                "");
    }
}
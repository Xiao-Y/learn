package com.billow.gateway.swagger2;

import jakarta.annotation.PostConstruct;
import org.springdoc.core.properties.AbstractSwaggerUiConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionLocator;
import org.springframework.context.annotation.Configuration;

import java.util.*;

@Configuration
public class OpenApiProvider {

    @Value("${springdoc.api-docs.path:/v3/api-docs}")
    private String API_URI;

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
//        // 默认折叠
//        swaggerUiConfigProperties.setDocExpansion("none");
//        // 额外配置：设置API文档标签按字母排序
//        swaggerUiConfigProperties.setOperationsSorter("alpha");
//        swaggerUiConfigProperties.setTagsSorter("alpha");
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
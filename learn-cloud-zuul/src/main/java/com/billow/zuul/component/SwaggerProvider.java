package com.billow.zuul.component;

import org.springframework.cloud.netflix.zuul.filters.Route;
import org.springframework.cloud.netflix.zuul.filters.RouteLocator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.List;

/**
 * swagger2 聚合
 *
 * @author billow
 * @date 2019/6/22 13:30
 */
@Component
@Primary
class SwaggerProvider implements SwaggerResourcesProvider {

    private final RouteLocator routeLocator;

    SwaggerProvider(RouteLocator routeLocator) {
        this.routeLocator = routeLocator;
    }

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList();
        resources.add(swaggerResource("cloud-zuul", "/v2/api-docs", "2.0"));
        List<Route> routes = routeLocator.getRoutes();
        routes.stream()
                //.filter(route -> !"/**".equals(route.getPath()))
                .forEach(route -> resources.add(swaggerResource(route.getId(),
                        route.getFullPath().replace("**", "v2/api-docs"), "2.0")));
        return resources;
    }

    /**
     * 构建 swagger 下拉列表中的选项
     *
     * @param name
     * @param location
     * @param version
     * @return springfox.documentation.swagger.web.SwaggerResource
     * @author LiuYongTao
     * @date 2019/6/21 14:30
     */
    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }
}
//package com.billow.gateway.component;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.cloud.gateway.route.RouteLocator;
//import org.springframework.context.annotation.Primary;
//import org.springframework.stereotype.Component;
//import springfox.documentation.swagger.web.SwaggerResource;
//import springfox.documentation.swagger.web.SwaggerResourcesProvider;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * swagger2 聚合
// *
// * @author billow
// * @date 2019/6/22 13:30
// */
//@Component
//@Primary
//public class SwaggerProvider implements SwaggerResourcesProvider {
//
//    public static final String API_URI = "/v2/api-docs";
//
//    private final RouteLocator routeLocator;
//
//    SwaggerProvider(RouteLocator routeLocator) {
//        this.routeLocator = routeLocator;
//    }
//
//    @Override
//    public List<SwaggerResource> get() {
//        List<SwaggerResource> resources = new ArrayList();
//        String sub = "/v2/api-docs";
//        resources.add(swaggerResource("cloud-zuul", sub, "2.0"));
//
//        routeLocator.getRoutes().subscribe(route -> {
//            String location = "/" + route.getId() + sub;
//            resources.add(swaggerResource(route.getId(), location, "2.0"));
//        });
//
//        return resources;
//    }
//
//    /**
//     * 构建 swagger 下拉列表中的选项
//     *
//     * @param name
//     * @param location
//     * @param version
//     * @return springfox.documentation.swagger.web.SwaggerResource
//     * @author LiuYongTao
//     * @date 2019/6/21 14:30
//     */
//    private SwaggerResource swaggerResource(String name, String location, String version) {
//        SwaggerResource swaggerResource = new SwaggerResource();
//        swaggerResource.setName(name);
//        swaggerResource.setLocation(location);
//        swaggerResource.setSwaggerVersion(version);
//        return swaggerResource;
//    }
//}
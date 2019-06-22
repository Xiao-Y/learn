package com.billow.zuul.component;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * swagger2 聚合
 *
 * @author billow
 * @date 2019/6/22 13:30
 */
@Component
@Primary
class DocumentationConfig implements SwaggerResourcesProvider, EnvironmentAware {

    private Set<String> routesSet = new HashSet<>();

    @Override
    public List<SwaggerResource> get() {
        List<SwaggerResource> resources = new ArrayList();
        resources.add(swaggerResource("cloud-zuul", "/v2/api-docs", "2.0"));
        routesSet.stream().forEach(f -> resources.add(swaggerResource(f, "/" + f + "/v2/api-docs", "2.0")));
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


    /**
     * 读取配置的路由数据
     *
     * @param environment
     * @return void
     * @author LiuYongTao
     * @date 2019/6/21 14:26
     */
    @Override
    public void setEnvironment(Environment environment) {
        RelaxedPropertyResolver propertyResolver = new RelaxedPropertyResolver(environment, null);
        Map<String, Object> subProperties = propertyResolver.getSubProperties("zuul.routes");
        subProperties.keySet().stream().forEach(f -> {
            String[] split = f.split("\\.");
            routesSet.add(split[1]);
        });
    }
}
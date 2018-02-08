package com.ft.swagger2;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * 用于接口文档
 *
 * @author liuyongtao
 * @create 2018-02-08 11:40
 */
@Configuration
@EnableSwagger2 // 启用Swagger2
public class Swagger2 extends WebMvcConfigurerAdapter implements EnvironmentAware {

    //    @Bean
//    public Docket createRestApi() {// 创建API基本信息
//        return new Docket(DocumentationType.SWAGGER_2)
//                .apiInfo(apiInfo())
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.ft.controller"))// 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
//                .paths(PathSelectors.any())
//                .build();
//    }
//
//    // 创建API的基本信息，这些信息会在Swagger UI中进行显示
//    private ApiInfo apiInfo() {
//        return new ApiInfoBuilder()
//                .title("learn 中使用Swagger2构建RESTful APIs")// API 标题
//                .description("learn-shop 提供的RESTful APIs")// API描述
//                .contact("lyongtao123@126.com")// 联系人
//                .version("1.0")// 版本号
//                .build();
//    }
    private String basePackage;
    private String creatName;
    private String serviceName;
    private RelaxedPropertyResolver propertyResolver;
    private String description;
    private String version;

    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"})
                .addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars*"})
                .addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }

    @Bean
    public Docket createRestApi() {
        return (new Docket(DocumentationType.SWAGGER_2))
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder())
                .title(this.serviceName + " Restful APIs")
                .description(this.description)
                .contact(this.creatName)
                .version(version)
                .build();
    }

    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, null);
        this.basePackage = this.propertyResolver.getProperty("swagger.basepackage");
        this.creatName = this.propertyResolver.getProperty("swagger.service.developer");
        this.serviceName = this.propertyResolver.getProperty("swagger.service.name");
        this.description = this.propertyResolver.getProperty("swagger.service.description");
        this.version = this.propertyResolver.getProperty("swagger.service.version");
    }
}

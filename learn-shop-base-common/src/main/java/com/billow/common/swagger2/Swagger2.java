package com.billow.common.swagger2;

import org.springframework.boot.bind.RelaxedPropertyResolver;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

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
//                .apis(RequestHandlerSelectors.basePackage("com.billow.api"))// 扫描该包下的所有需要在Swagger中展示的API，@ApiIgnore注解标注的除外
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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(new String[]{"swagger-ui.html"})
                .addResourceLocations(new String[]{"classpath:/META-INF/resources/"});
        registry.addResourceHandler(new String[]{"/webjars/**"})
                .addResourceLocations(new String[]{"classpath:/META-INF/resources/webjars/"});
    }

    @Bean
    public Docket createRestApi() {

        //添加head参数start
        ParameterBuilder tokenPar = new ParameterBuilder();
        List<Parameter> pars = new ArrayList<>();
        tokenPar.name("access_token")
                .description("请输入您的Access Token")
                .modelRef(new ModelRef("string"))
                .parameterType("query")//参数类型支持header, cookie, body, query
                .required(false)
                .build();
        pars.add(tokenPar.build());

        return (new Docket(DocumentationType.SWAGGER_2))
                .apiInfo(this.apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage(this.basePackage))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(pars);
    }

    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder())
                .title(this.serviceName + " Restful APIs")
                .description(this.description)
                .contact(new Contact(this.creatName, "", ""))
                .version(version)
                .build();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.propertyResolver = new RelaxedPropertyResolver(environment, null);
        this.basePackage = this.propertyResolver.getProperty("swagger.basepackage");
        this.creatName = this.propertyResolver.getProperty("swagger.service.developer");
        this.serviceName = this.propertyResolver.getProperty("swagger.service.name");
        this.description = this.propertyResolver.getProperty("swagger.service.description");
        this.version = this.propertyResolver.getProperty("swagger.service.version");
    }
}

package com.billow.auth.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
public class Swagger2 {

    @Value("${swagger.basepackage}")
    private String basePackage;
    @Value("${swagger.service.name}")
    private String serviceName;
    @Value("${swagger.service.description}")
    private String description;
    @Value("${swagger.service.version}")
    private String version;
    @Value("${swagger.service.contact.developer}")
    private String developer;
    @Value("${swagger.service.contact.url}")
    private String url;
    @Value("${swagger.service.contact.email}")
    private String email;

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
                .contact(new Contact(this.developer, this.url, this.email))
                .version(version)
                .build();
    }
}

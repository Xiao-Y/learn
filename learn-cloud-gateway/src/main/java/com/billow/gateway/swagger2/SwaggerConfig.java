package com.billow.gateway.swagger2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;

import java.util.*;

/**
 * swagger配制
 *
 * @author liuyongtao
 * @since 2021-1-21 14:04
 */
@Configuration
public class SwaggerConfig {

    @Value("${swagger.enable:true}")
    private Boolean enable;

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

    @Value("${secure.client.access-token-uri}")
    private String tokenUrl;
    @Value("${secure.client.client-id}")
    private String clientId;
    @Value("${secure.client.client-secret}")
    private String clientSecret;
    @Value("${secure.client.scope}")
    private String scope;

    public static final String SCHEME_NAME_OAUTH2 = "OAuth 2.0 Authentication";

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.OAS_30)
                //资源
                .pathMapping("/")
                //是否启动
                .enable(enable)
                //头部信息
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.any())
                //过滤某个路径
                .paths(PathSelectors.any())
                .build()
                //协议
                .protocols(new LinkedHashSet<>(Arrays.asList("https", "http")))
                // 安全设置
                .securityContexts(securityContexts())
                .securitySchemes(securitySchemes());
    }

    /**
     * 设置默认参数
     *
     * @return {@link SecurityConfiguration}
     * @author liuyongtao
     * @since 2021-1-21 14:06
     */
    @Bean
    public SecurityConfiguration security() {
        return SecurityConfigurationBuilder.builder()
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scopeSeparator(scope)
                .build();
    }

    /**
     * API 页面上半部分展示信息
     *
     * @return {@link ApiInfo}
     * @author liuyongtao
     * @since 2021-1-21 14:05
     */
    private ApiInfo apiInfo() {
        return (new ApiInfoBuilder())
                .title(this.serviceName + " Restful APIs")
                .description(this.description)
                .contact(new Contact(this.developer, this.url, this.email))
                .version(version)
                .build();
    }

    /**
     * 设置授权信息
     *
     * @return {@link List< SecurityScheme>}
     * @author liuyongtao
     * @since 2021-1-21 11:15
     */
    private List<SecurityScheme> securitySchemes() {
        OAuth2Scheme scheme = new OAuth2Scheme(SCHEME_NAME_OAUTH2, "password", "OAuth2 密码模式认证",
                "", tokenUrl, "", Arrays.asList(scoper()), new ArrayList<>());
        return Collections.singletonList(scheme);
    }

    /**
     * swagger2 认证的安全上下文
     *
     * @return {@link List< SecurityContext>}
     * @author liuyongtao
     * @since 2021-1-21 14:04
     */
    private List<SecurityContext> securityContexts() {
        return Collections.singletonList(SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build());
    }

    private List<SecurityReference> defaultAuth() {
        return Collections.singletonList(new SecurityReference(SCHEME_NAME_OAUTH2, scoper()));
    }

    private AuthorizationScope[] scoper() {
        return new AuthorizationScope[]{
                new AuthorizationScope(scope, scope + " scope is trusted!")
        };
    }
}


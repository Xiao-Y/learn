package com.billow.gateway.swagger2;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {


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

    /**
     * API 页面上半部分展示信息
     *
     * @return
     * @author 千面
     * @date 2025-08-21 08:50:10
     */
    @Bean
    public OpenAPI customOpenAPI() {

        Contact contact = new Contact();
        contact.setEmail(this.email);
        contact.setUrl(this.url);
        contact.setName(this.developer);

        return new OpenAPI()
                .info(new Info()
                        .title(this.serviceName + " Restful APIs")
                        .version(this.version)
                        .contact(contact)
                        .description(this.description));
    }
}
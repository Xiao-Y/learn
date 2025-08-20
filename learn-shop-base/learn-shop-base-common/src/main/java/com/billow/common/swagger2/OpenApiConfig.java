package com.billow.common.swagger2;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    public static final String API_URI = "/v3/api-docs";

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
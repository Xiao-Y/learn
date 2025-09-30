package com.billow.system.common.config;

import com.billow.system.common.properties.CommonProperties;
import com.billow.system.common.properties.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 资源映射路径
 *
 * @author LiuYongTao
 * @date 2019/7/26 16:30
 */
@Configuration
public class CustomWebAppConfigurer extends WebMvcConfigurationSupport {

    @Autowired
    private CustomProperties customProperties;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        CommonProperties common = customProperties.getCommon();
        // /displayImag
        String imageMapping = common.getImageMapping();
        // markdown
        String markdownImgPath = common.getMarkdownImgPath();
        // user icon /usericon
        String userIconImgPath = common.getUserIconImgPath();
        // markdown
        registry.addResourceHandler(imageMapping + markdownImgPath + "/**")
                .addResourceLocations("file:" + common.getBaseFilePath() + markdownImgPath + "/");
        // user icon
        registry.addResourceHandler(imageMapping + userIconImgPath + "/**")
                .addResourceLocations("file:" + common.getBaseFilePath() + userIconImgPath + "/");
    }
}
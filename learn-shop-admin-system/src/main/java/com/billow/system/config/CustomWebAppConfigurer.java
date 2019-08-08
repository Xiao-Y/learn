package com.billow.system.config;

import com.billow.system.properties.CommonProperties;
import com.billow.system.properties.CustomProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * 资源映射路径
 *
 * @author LiuYongTao
 * @date 2019/7/26 16:30
 */
@Configuration
public class CustomWebAppConfigurer extends WebMvcConfigurerAdapter {

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
        // 获取系统类型
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            // markdown
            registry.addResourceHandler(imageMapping + markdownImgPath + "/**")
                    .addResourceLocations("file:" + common.getWin().getBaseFilePath() + markdownImgPath + "/");
            // user icon
            registry.addResourceHandler(imageMapping + userIconImgPath + "/**")
                    .addResourceLocations("file:" + common.getWin().getBaseFilePath() + userIconImgPath + "/");
        } else {//linux 和mac
            // markdown
            registry.addResourceHandler(imageMapping + markdownImgPath + "/**")
                    .addResourceLocations("file:" + common.getLinux().getBaseFilePath() + markdownImgPath + "/");
            // user icon
            registry.addResourceHandler(imageMapping + userIconImgPath + "/**")
                    .addResourceLocations("file:" + common.getLinux().getBaseFilePath() + userIconImgPath + "/");
        }
    }
}
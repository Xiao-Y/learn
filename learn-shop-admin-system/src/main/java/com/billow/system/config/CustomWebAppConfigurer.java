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
        String os = System.getProperty("os.name");
        if (os.toLowerCase().startsWith("win")) {  //如果是Windows系统
            registry.addResourceHandler(common.getWordResourceDandler() + "/**")
                    .addResourceLocations("file:" + common.getWin().getWordImgPath());
        } else {//linux 和mac
            registry.addResourceHandler(common.getWordResourceDandler() + "/**")
                    .addResourceLocations("file:" + common.getLinux().getWordImgPath());
        }
    }
}
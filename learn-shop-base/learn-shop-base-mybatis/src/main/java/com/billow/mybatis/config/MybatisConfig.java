package com.billow.mybatis.config;

import com.billow.mybatis.handler.MybatisFlexAuditMetaObjectHandler;
import com.billow.mybatis.pojo.BasePo;
import com.billow.mybatis.utils.MybatisUserTools;
import com.mybatisflex.core.FlexGlobalConfig;
import com.mybatisflex.core.mybatis.FlexConfiguration;
import com.mybatisflex.spring.boot.ConfigurationCustomizer;
import org.apache.ibatis.logging.stdout.StdOutImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MybatisConfig implements ConfigurationCustomizer {

    /**
     * 配置mybatis日志
     *
     * @param configuration
     * @author LiuYongTao
     * @date 2019/11/1 10:40
     */
    @Override
    public void customize(FlexConfiguration configuration) {
        configuration.setLogImpl(StdOutImpl.class);
    }

    @Bean
    public MybatisUserTools mybatisUserTools() {
        return new MybatisUserTools();
    }

//    /**
//     * 审计数据插件
//     *
//     * @return AuditMetaObjectHandler
//     */
//    @Bean
//    @ConditionalOnMissingBean(name = "auditMetaObjectHandler")
//    public MybatisPlusAuditMetaObjectHandler auditMetaObjectHandler() {
//        return new MybatisPlusAuditMetaObjectHandler();
//    }

    /**
     * mybatis 分页插件
     *
     * @return com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor
     * @author LiuYongTao
     * @date 2019/11/1 10:41
     */
//    @Bean
//    public MybatisPlusInterceptor paginationInterceptor() {
//        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
//        interceptor.addInnerInterceptor(new PaginationInnerInterceptor(DbType.MYSQL));
//        return interceptor;
//    }
    @Bean
    public FlexGlobalConfig flexGlobalConfig() {
        MybatisFlexAuditMetaObjectHandler mybatisInsertListener = new MybatisFlexAuditMetaObjectHandler();
        FlexGlobalConfig config = FlexGlobalConfig.getDefaultConfig();
        //设置BaseEntity类启用
        config.registerInsertListener(mybatisInsertListener, BasePo.class);
        return config;
    }
}

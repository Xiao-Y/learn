package com.billow.common.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.billow.cloud.common.properties.DruidDSProperties;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DatabaseDriver;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import java.sql.SQLException;

/**
 * druid数据源
 *
 * @author liuyongtao
 * @create 2018-02-08 8:43
 */
@Configuration
@ConditionalOnClass(DruidDataSource.class) //该注解的参数对应的类必须存在，否则不解析该注解修饰的配置类
@EnableConfigurationProperties(DruidDSProperties.class) // 用于@Autowired注入
public class DruidConfiguration {

    @Autowired
    private DruidDSProperties druidDSProperties;

    @Bean(name = "dataSource", destroyMethod = "close", initMethod = "init")
    public DruidDataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(druidDSProperties.getDriverClassName());
        dataSource.setUrl(druidDSProperties.getUrl());
        dataSource.setUsername(druidDSProperties.getUsername());
        dataSource.setPassword(druidDSProperties.getPassword());
        dataSource.setInitialSize(druidDSProperties.getInitialSize());
        dataSource.setMinIdle(druidDSProperties.getMinIdle());
        dataSource.setMaxActive(druidDSProperties.getMaxActive());
        dataSource.setMaxWait(druidDSProperties.getMaxWait());
        dataSource.setTestOnReturn(druidDSProperties.getTestOnReturn());
        dataSource.setPoolPreparedStatements(druidDSProperties.getPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(druidDSProperties.getMaxPoolPreparedStatementPerConnectionSize());
        dataSource.setTimeBetweenEvictionRunsMillis(druidDSProperties.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(druidDSProperties.getMinEvictableIdleTimeMillis());

        dataSource.configFromPropety(druidDSProperties.toProperties());

        DatabaseDriver databaseDriver = DatabaseDriver.fromJdbcUrl(druidDSProperties.getUrl());
        String validationQuery = databaseDriver.getValidationQuery();
        if (validationQuery != null) {
            dataSource.setTestOnBorrow(true);
            dataSource.setValidationQuery(validationQuery);
        }
        try {
            // 开启Druid的监控统计功能，mergeStat代替stat表示sql合并,wall表示防御SQL注入攻击
            dataSource.setFilters("mergeStat,wall,log4j");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return dataSource;
    }

    /**
     * 注册一个StatViewServlet
     *
     * @return org.springframework.boot.web.servlet.ServletRegistrationBean
     * @author LiuYongTao
     * @date 2018/10/23 16:13
     */
    @Bean
    public ServletRegistrationBean druidStatViewServlet() {
        // org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet(), "/druid/*");
        // 添加初始化参数：initParams
        // 白名单：
        // servletRegistrationBean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
        // servletRegistrationBean.addInitParameter("deny", "192.168.1.73");
        // 登录查看信息的账号密码.
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "123456");
        // 是否能够重置数据.
        servletRegistrationBean.addInitParameter("resetEnable", "false");
        return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return org.springframework.boot.web.servlet.FilterRegistrationBean
     * @author LiuYongTao
     * @date 2018/10/23 16:13
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则.
        filterRegistrationBean.addUrlPatterns("/*");
        // 添加不需要忽略的格式信息.
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }

    /**
     * druidStat 拦截器
     *
     * @return com.alibaba.druid.support.spring.stat.DruidStatInterceptor
     * @author LiuYongTao
     * @date 2018/10/23 16:13
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
        DruidStatInterceptor dsInterceptor = new DruidStatInterceptor();
        return dsInterceptor;
    }

    /**
     * druidStat 切点
     *
     * @return org.springframework.aop.support.JdkRegexpMethodPointcut
     * @author LiuYongTao
     * @date 2018/10/23 16:14
     */
    @Bean
    @Scope("prototype")
    public JdkRegexpMethodPointcut druidStatPointcut() {
        JdkRegexpMethodPointcut pointcut = new JdkRegexpMethodPointcut();
        pointcut.setPattern("com.billow.*.service.*");
        return pointcut;
    }

    /**
     * druidStat配置通知，为了拦截service 信息
     *
     * @param druidStatInterceptor 拦截器
     * @param druidStatPointcut    切点
     * @return org.springframework.aop.support.DefaultPointcutAdvisor
     * @author LiuYongTao
     * @date 2018/10/23 16:14
     */
    @Bean
    public DefaultPointcutAdvisor druidStatAdvisor(DruidStatInterceptor druidStatInterceptor, JdkRegexpMethodPointcut druidStatPointcut) {
        DefaultPointcutAdvisor defaultPointAdvisor = new DefaultPointcutAdvisor();
        defaultPointAdvisor.setPointcut(druidStatPointcut);
        defaultPointAdvisor.setAdvice(druidStatInterceptor);
        return defaultPointAdvisor;
    }
}

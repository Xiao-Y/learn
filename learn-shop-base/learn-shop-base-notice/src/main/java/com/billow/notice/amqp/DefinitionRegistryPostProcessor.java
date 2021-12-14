package com.billow.notice.amqp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @Configuration 或
 * @Component 或
 * @Bean定义
 **/
@Configuration
public class DefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    private static BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    private ApplicationContext applicationContext;

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException {

    }

    /**
     * 先执行postProcessBeanDefinitionRegistry方法
     * 在执行postProcessBeanFactory方法
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        // 校验 bean 是否存在
//        applicationContext.containsBean()
        // https://blog.csdn.net/zxx2096/article/details/116146785 配置文件格式
        // 第一种 ： 手动注入
        // 注册bean
        String queueName = "testQueue";
        String directExchangeName = "testDirectExchange";
        String routeKey = "testRouteKey";
        Binding.DestinationType destinationType = Binding.DestinationType.QUEUE;

        registerBean(registry, "testQueue", Queue.class, queueName);
        registerBean(registry, "testDirectExchange", DirectExchange.class, directExchangeName);
        registerBean(registry, "testBinding", Binding.class, queueName, destinationType,
                directExchangeName, routeKey, new HashMap<String, Object>());

    }

    /**
     * 主动向Spring容器中注册bean
     *
     * @param registry  Spring bean 注册容器
     * @param name      BeanName
     * @param beanClass 注册的bean的类性
     * @param args      构造方法的必要参数，顺序和类型要求和clazz中定义的一致
     * @param <T>
     * @return 返回注册到容器中的bean对象
     */
    public static <T> T registerBean(BeanDefinitionRegistry registry, String name, Class<T> beanClass, Object... args) {
        // 可以自动生成name
        if (StringUtils.isEmpty(name)) {
            AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
            name = beanNameGenerator.generateBeanName(abd, registry);
        }

        // 设置构造参数
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        for (Object arg : args) {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();

        // 为创建的bean里面的属性赋值
//        MutablePropertyValues propertyValues = beanDefinition.getPropertyValues();
//        propertyValues.add("descritpion", "我是填充进来的");

        registry.registerBeanDefinition(name, beanDefinition);
        return null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}
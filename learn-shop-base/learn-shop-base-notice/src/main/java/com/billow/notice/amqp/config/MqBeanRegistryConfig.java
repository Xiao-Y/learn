package com.billow.notice.amqp.config;

import com.billow.notice.amqp.properties.MqSetting;
import com.billow.notice.amqp.properties.NoticeMqYml;
import com.billow.notice.config.SpringContextUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.AnnotatedGenericBeanDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * 根据配置文件队列、路由、交换机的绑定
 *
 * @Configuration 或
 * @Component 或
 * @Bean定义
 **/
@Slf4j
@Configuration
public class MqBeanRegistryConfig implements BeanDefinitionRegistryPostProcessor
{
    /**
     * 获取 ApplicationContext
     *
     * @author 千面
     * @date 2021/12/15 8:38
     */
    private static ApplicationContext applicationContext = SpringContextUtil.getApplicationContext();

    /**
     * 自动生成 bean 名称
     *
     * @author 千面
     * @date 2021/12/15 8:38
     */
    private static BeanNameGenerator beanNameGenerator = new AnnotationBeanNameGenerator();

    @Autowired
    private NoticeMqYml noticeMqYml;

    /**
     * 获取 mq 的设置属性
     *
     * @param
     * @return List<MqSetting> 设置属性
     * @author 千面
     * @date 2021/12/15 8:44
     */
    private List<MqSetting> getMqSettingList()
    {
        if (Objects.isNull(noticeMqYml))
        {
            return new ArrayList<>();
        }

        List<MqSetting> mqSettings = noticeMqYml.getMqCollect();
        if (CollectionUtils.isEmpty(mqSettings))
        {
            return new ArrayList<>();
        }
        return mqSettings;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory factory) throws BeansException
    {

    }

    /**
     * 先执行postProcessBeanDefinitionRegistry方法
     * 在执行postProcessBeanFactory方法
     */
    @SneakyThrows
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException
    {
        List<MqSetting> mqSettingList = this.getMqSettingList();
        log.info("====================== 开始注入 MQ 设置:{} ======================", mqSettingList.size());
        for (MqSetting mqSetting : mqSettingList)
        {
            // 配置数据检查
            this.checkMqSetting(mqSetting);
            // 注入队列
            this.registryQueue(registry, mqSetting.getQueue());
            // 注入交换机
            this.registryExchange(registry, mqSetting.getExchangeType(), mqSetting.getExchange());
            // 绑定队列和交换机
            this.registryBinding(registry, mqSetting);
        }
        log.info("====================== 结束注入 MQ 设置 ======================");
    }

    /**
     * 绑定队列和交换机
     *
     * @param registry
     * @param mqSetting mq 配置
     * @return void
     * @author 千面
     * @date 2021/12/15 9:16
     */
    private void registryBinding(BeanDefinitionRegistry registry, MqSetting mqSetting)
    {
        String queueName = mqSetting.getQueue();
        String bindingName = queueName + "Binding";
        registerBean(registry, bindingName, Binding.class, mqSetting.getQueue(), Binding.DestinationType.QUEUE,
                mqSetting.getExchange(), mqSetting.getRouteKey(), new HashMap<String, Object>());
        log.info("exchangeName:{},queueName:{},routeKey:{},bindingName:{} 绑定成功", mqSetting.getExchange(), mqSetting.getQueue(),
                mqSetting.getRouteKey(), bindingName);
    }

    /**
     * 注入交换机
     *
     * @param registry
     * @param exchangeType 交换机类型
     * @param exchangeName 交换机名称
     * @return void
     * @author 千面
     * @date 2021/12/15 9:16
     */
    private void registryExchange(BeanDefinitionRegistry registry, String exchangeType, String exchangeName) throws ClassNotFoundException
    {
        boolean exchangeFlag = applicationContext.containsBean(exchangeName);
        if (!exchangeFlag)
        {
            // 交换机类型
            Class<?> exchangeClass;
            switch (exchangeType)
            {
                case ExchangeTypes.DIRECT:
                    exchangeClass = DirectExchange.class;
                    break;
                case ExchangeTypes.FANOUT:
                    exchangeClass = FanoutExchange.class;
                    break;
                case ExchangeTypes.TOPIC:
                    exchangeClass = TopicExchange.class;
                    break;
                default:
                    throw new ClassNotFoundException("交换机：" + exchangeName + "，没有找到对应的交换机类型:" + exchangeType);
            }
            this.registerBean(registry, exchangeClass, exchangeName);
        }
        else
        {
            log.warn("交换机：{} 已经存在", exchangeName);
        }
    }

    /**
     * 注入队列
     *
     * @param registry
     * @param queueName 队列名称
     * @return void
     * @author 千面
     * @date 2021/12/15 9:16
     */
    private void registryQueue(BeanDefinitionRegistry registry, String queueName)
    {
        boolean queueFlag = applicationContext.containsBean(queueName);
        if (!queueFlag)
        {
            this.registerBean(registry, Queue.class, queueName);
        }
        else
        {
            log.warn("队列：{} 已经存在", queueName);
        }
    }

    /**
     * 配置数据检查
     *
     * @param mqSetting mq配置
     * @return void
     * @author 千面
     * @date 2021/12/15 9:09
     */
    private void checkMqSetting(MqSetting mqSetting)
    {
        String queueName = mqSetting.getQueue();
        if (StringUtils.isEmpty(queueName))
        {
            log.error("MQ 配置===>>>队列名称不能为空");
            throw new NullPointerException();
        }
        String exchangeName = mqSetting.getExchange();
        if (StringUtils.isEmpty(exchangeName))
        {
            log.error("MQ 配置===>>>交换机名称不能为空");
            throw new NullPointerException();
        }
        String routeKey = mqSetting.getRouteKey();
        if (StringUtils.isEmpty(routeKey))
        {
            log.error("MQ 配置===>>>路由key名称不能为空");
            throw new NullPointerException();
        }
    }

    /**
     * @param registry  Spring bean 注册容器
     * @param beanClass 注册的bean的类性
     * @param args      构造方法的必要参数，顺序和类型要求和clazz中定义的一致
     * @return T 返回注册到容器中的bean对象
     * @author 千面
     * @date 2021/12/15 8:39
     */
    private <T> T registerBean(BeanDefinitionRegistry registry, Class<T> beanClass, Object... args)
    {
        return this.registerBean(registry, null, beanClass, args);
    }

    /**
     * @param registry  Spring bean 注册容器
     * @param beanName  注册的bean的名称
     * @param beanClass 注册的bean的类性
     * @param args      构造方法的必要参数，顺序和类型要求和clazz中定义的一致
     * @return T 返回注册到容器中的bean对象
     * @author 千面
     * @date 2021/12/15 8:39
     */
    private <T> T registerBean(BeanDefinitionRegistry registry, String beanName, Class<T> beanClass, Object... args)
    {
        // 如果为空，自动生成 beanName
        if (StringUtils.isEmpty(beanName))
        {
            AnnotatedGenericBeanDefinition abd = new AnnotatedGenericBeanDefinition(beanClass);
            beanName = beanNameGenerator.generateBeanName(abd, registry);
        }
        // 设置构造参数
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        for (Object arg : args)
        {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        // 注入 bean 定义
        registry.registerBeanDefinition(beanName, beanDefinition);
        return applicationContext.getBean(beanName, beanClass);
    }

}
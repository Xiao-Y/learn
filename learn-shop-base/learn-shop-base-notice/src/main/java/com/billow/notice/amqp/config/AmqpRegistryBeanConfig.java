package com.billow.notice.amqp.config;

import com.billow.notice.amqp.properties.MqSetting;
import com.billow.notice.amqp.properties.NoticeMqYml;
import com.billow.notice.config.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 获取 ApplicationContext
 *
 * @author 千面
 * @date 2021/12/15 8:33
 */
@Slf4j
@Configuration
public class AmqpRegistryBeanConfig implements ApplicationContextAware
{
    private ApplicationContext applicationContext;
    private DefaultListableBeanFactory defaultListableBeanFactory;

    @Autowired
    private NoticeMqYml noticeMqYml;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException
    {
        this.applicationContext = applicationContext;
        //将applicationContext转换为ConfigurableApplicationContext
        ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
        // 获取bean工厂并转换为DefaultListableBeanFactory
        defaultListableBeanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        // 保存 applicationContext
        SpringContextUtil.setApplicationContext(applicationContext);
    }

    @PostConstruct
    public void afterPropertiesSet() throws Exception
    {
        /// https://www.cnblogs.com/Chary/p/14361830.html
        // 注册bean
        Map<String, MqSetting> mqSettingMap = this.getMqSettingList();
        log.info("====================== 开始注入 MQ 设置:{} ======================", mqSettingMap.size());
        for (String key : mqSettingMap.keySet())
        {
            MqSetting mqSetting = mqSettingMap.get(key);
            // 配置数据检查
            this.checkMqSetting(key, mqSetting);
            // 注入队列
            this.registryQueue(mqSetting);
            // 注入交换机
            this.registryExchange(mqSetting.getExchangeType(), mqSetting.getExchange());
            // 绑定队列和交换机
            String bindingName = this.registryBinding(key, mqSetting);
            log.info("exchangeName:{},queueName:{},routeKey:{},bindingName:{} 绑定成功", mqSetting.getExchange(), mqSetting.getQueue(),
                    mqSetting.getRouteKey(), bindingName);

            System.out.println(SpringContextUtil.getBean(bindingName, Binding.class));
        }
        log.info("====================== 结束注入 MQ 设置 ======================");
    }

    /**
     * 获取 mq 的设置属性
     *
     * @param
     * @return List<MqSetting> 设置属性
     * @author 千面
     * @date 2021/12/15 8:44
     */
    private Map<String, MqSetting> getMqSettingList()
    {
        if (Objects.isNull(noticeMqYml))
        {
            return new HashMap<>();
        }

        Map<String, MqSetting> mqSettings = noticeMqYml.getMqCollect();
        if (CollectionUtils.isEmpty(mqSettings))
        {
            return new HashMap<>();
        }
        return mqSettings;
    }

    /**
     * 绑定队列和交换机
     *
     * @param mqSettingName 单项mq配置名称
     * @param mqSetting     mq 配置
     * @return bindingName 绑定的name
     * @author 千面
     * @date 2021/12/15 9:16
     */
    private String registryBinding(String mqSettingName, MqSetting mqSetting)
    {
        String bindingName = mqSettingName + MqConstant.SUFFIX_BINDING;
        boolean bindingFlag = applicationContext.containsBean(bindingName);
        if (bindingFlag)
        {
            log.warn("绑定名称：{} 已经存在", bindingFlag);
            return bindingName;
        }
        registerBean(bindingName, Binding.class, mqSetting.getQueue(), Binding.DestinationType.QUEUE,
                mqSetting.getExchange(), mqSetting.getRouteKey(), new HashMap<String, Object>());
        return bindingName;
    }

    /**
     * 注入交换机
     *
     * @param exchangeType 交换机类型
     * @param exchangeName 交换机名称
     * @return void
     * @author 千面
     * @date 2021/12/15 9:16
     */
    private void registryExchange(String exchangeType, String exchangeName) throws ClassNotFoundException
    {
        boolean exchangeFlag = applicationContext.containsBean(exchangeName);
        if (exchangeFlag)
        {
            log.warn("交换机：{} 已经存在", exchangeName);
            return;
        }
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
        this.registerBean(exchangeName, exchangeClass, exchangeName);
    }

    /**
     * 注入队列
     *
     * @param mqSetting mq配置
     * @return void
     * @author 千面
     * @date 2021/12/15 9:16
     */
    private void registryQueue(MqSetting mqSetting)
    {
        String queueName = mqSetting.getQueue();
        boolean queueFlag = applicationContext.containsBean(queueName);
        if (queueFlag)
        {
            log.warn("队列：{} 已经存在", queueName);
        }
        this.registerBean(queueName, Queue.class, queueName, mqSetting.getDurable());
    }

    /**
     * 配置数据检查
     *
     * @param mqSettingName 单项mq配置名称
     * @param mqSetting     mq配置
     * @return void
     * @author 千面
     * @date 2021/12/15 9:09
     */
    private void checkMqSetting(String mqSettingName, MqSetting mqSetting)
    {
        String queueName = mqSetting.getQueue();
        if (StringUtils.isEmpty(queueName))
        {
            log.warn("MQ 配置===>>>队列名称为空,自动生成");
            mqSetting.setQueue(mqSettingName + MqConstant.SUFFIX_QUEUE);
        }
        String exchangeName = mqSetting.getExchange();
        if (StringUtils.isEmpty(exchangeName))
        {
            log.warn("MQ 配置===>>>交换机名称为空,自动生成");
            mqSetting.setExchange(mqSettingName + MqConstant.SUFFIX_EXCHANGE);
        }
        String routeKey = mqSetting.getRouteKey();
        if (StringUtils.isEmpty(routeKey))
        {
            log.error("MQ 配置===>>>路由key名称为空,自动生成");
            mqSetting.setRouteKey(mqSettingName + MqConstant.SUFFIX_ROUTE_KEY);
        }
    }

    /**
     * 注册bean
     *
     * @param beanName  注册的bean的名称
     * @param beanClass 注册的bean的类性
     * @param args      构造方法的必要参数，顺序和类型要求和clazz中定义的一致
     * @return T 返回注册到容器中的bean对象
     * @author 千面
     * @date 2021/12/15 8:39
     */
    private <T> void registerBean(String beanName, Class<T> beanClass, Object... args)
    {
        // 如果为空，自动生成 beanName
        if (StringUtils.isEmpty(beanName))
        {
            log.error("beanClass:{}，beanName 不能为空", beanClass.getName(), beanName);
            throw new RuntimeException();
        }
        // // 通过BeanDefinitionBuilder创建bean定义，设置构造参数
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(beanClass);
        for (Object arg : args)
        {
            beanDefinitionBuilder.addConstructorArgValue(arg);
        }
        BeanDefinition beanDefinition = beanDefinitionBuilder.getRawBeanDefinition();
        // 注入 bean 定义
        defaultListableBeanFactory.registerBeanDefinition(beanName, beanDefinition);
    }

}

package com.billow.notice.amqp.config;

import com.billow.notice.amqp.properties.MqSetting;
import com.billow.notice.amqp.properties.NoticeMqYml;
import jakarta.annotation.PostConstruct;
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

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * 获取 ApplicationContext，注册配置文件中的队列、交换机、绑定路由
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
    }

    @PostConstruct
    public void dynamicRegistryMQ() throws Exception
    {
        // 获取配置文件
        Map<String, MqSetting> mqSettingMap = this.getMqSettingList();
        // 转换生成需要构建 bean 的数据
        Map<String, MqSetting> appendMqSetting = this.convertMqSetting(mqSettingMap);
        if (!CollectionUtils.isEmpty(appendMqSetting))
        {
            mqSettingMap.putAll(appendMqSetting);
        }
        log.info("====================== 开始注入 MQ 设置:{} ======================", mqSettingMap.size());
        for (String key : mqSettingMap.keySet())
        {
            MqSetting mqSetting = mqSettingMap.get(key);
            // 配置数据检查
            this.checkMqSetting(key, mqSetting);
            // 注入队列
            boolean registryQueue = this.registryQueue(mqSetting);
            // 注入交换机
            boolean registryExchange = this.registryExchange(mqSetting);
            // 绑定队列和交换机
            if (!registryExchange && !registryQueue)
            {
                continue;
            }
            String bindingName = this.registryBinding(key, mqSetting);
            log.info("exchangeName:{},queueName:{},routeKey:{},bindingName:{} 绑定成功", mqSetting.getExchange(), mqSetting.getQueue(),
                    mqSetting.getRouteKey(), bindingName);
        }
        log.info("====================== 结束注入 MQ 设置 ======================");
    }

    /**
     * 转换生成需要构建 bean 的数据
     *
     * @param mqSettingMap
     * @return Map<String, MqSetting>
     * @author 千面
     * @date 2021/12/17 16:06
     */
    private Map<String, MqSetting> convertMqSetting(Map<String, MqSetting> mqSettingMap)
    {
        Map<String, MqSetting> appendMqSetting = new HashMap<>();
        Set<String> keySet = mqSettingMap.keySet();
        for (String key : keySet)
        {
            MqSetting mqSetting = mqSettingMap.get(key);
            String dlxExchange = mqSetting.getDlxExchange();
            String dlxQueue = mqSetting.getDlxQueue();
            String dlxExchangeType = mqSetting.getDlxExchangeType();
            String dlxRouteKey = mqSetting.getDlxRouteKey();
            if (StringUtils.isEmpty(dlxExchange) || StringUtils.isEmpty(dlxQueue))
            {
                continue;
            }
            MqSetting temp = new MqSetting();
            temp.setExchange(dlxExchange);
            temp.setQueue(dlxQueue);
            temp.setRouteKey(dlxRouteKey);
            temp.setDlxExchangeType(dlxExchangeType);
            String tempKey = key + MqConstant.SUFFIX_BINDING_DLX;
            appendMqSetting.put(tempKey, temp);
        }
        return appendMqSetting;
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
     * @param mqSetting mq 配置
     * @return void
     * @author 千面
     * @date 2021/12/15 9:16
     */
    private boolean registryExchange(MqSetting mqSetting) throws ClassNotFoundException
    {
        // 交换机类型
        String exchangeType = mqSetting.getExchangeType();
        // 交换机名称
        String exchangeName = mqSetting.getExchange();
        boolean exchangeFlag = applicationContext.containsBean(exchangeName);
        if (exchangeFlag)
        {
            log.warn("交换机：{} 已经存在", exchangeName);
            return false;
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
                log.error("交换机：{}，没有找到对应的交换机类型:{}", exchangeName, exchangeType);
                throw new ClassNotFoundException();
        }
        return this.registerBean(exchangeName, exchangeClass, exchangeName);
    }

    /**
     * 注入队列
     *
     * @param mqSetting mq配置
     * @return void
     * @author 千面
     * @date 2021/12/15 9:16
     */
    private boolean registryQueue(MqSetting mqSetting)
    {
        String queueName = mqSetting.getQueue();
        boolean queueFlag = applicationContext.containsBean(queueName);
        if (queueFlag)
        {
            log.warn("队列：{} 已经存在", queueName);
            return false;
        }
        // 延时队列转发的死信交换机上
        Map<String, Object> arguments = new HashMap<>();
        String dlxExchange = mqSetting.getDlxExchange();
        if (StringUtils.hasText(dlxExchange))
        {
            arguments.put(MqConstant.X_DEAD_LETTER_EXCHANGE, dlxExchange);
            arguments.put(MqConstant.X_MESSAGE_TTL, mqSetting.getMessageTtl());
            String dlxRouteKey = mqSetting.getDlxRouteKey();
            if (StringUtils.hasText(dlxRouteKey))
            {
                arguments.put(MqConstant.X_DEAD_LETTER_ROUTING_KEY, dlxRouteKey);
            }
            mqSetting.setDurable(true);
        }
        // 注册 bean
        // Queue(String name, boolean durable, boolean exclusive, boolean autoDelete, @Nullable Map<String, Object> arguments)
        return this.registerBean(queueName, Queue.class, queueName, mqSetting.getDurable(),
                false, false, arguments);
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
        log.info("mqSettingName:{},mqSetting{}", mqSettingName, mqSetting);
        String queueName = mqSetting.getQueue();
        if (StringUtils.isEmpty(queueName))
        {
            mqSetting.setQueue(mqSettingName + MqConstant.SUFFIX_QUEUE);
            log.warn("MQ 配置{}===>>>队列名称为空,自动生成：{}", mqSetting.getQueue());
        }
        String exchangeName = mqSetting.getExchange();
        if (StringUtils.isEmpty(exchangeName))
        {
            mqSetting.setExchange(mqSettingName + MqConstant.SUFFIX_EXCHANGE);
            log.warn("MQ 配置===>>>交换机名称为空,自动生成：{}", mqSetting.getExchange());
        }
        String routeKey = mqSetting.getRouteKey();
        if (StringUtils.isEmpty(routeKey))
        {
            mqSetting.setRouteKey("");
            log.warn("MQ 配置===>>>路由key名称为空,使用默认路由");
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
    private <T> boolean registerBean(String beanName, Class<T> beanClass, Object... args)
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
        return true;
    }

}

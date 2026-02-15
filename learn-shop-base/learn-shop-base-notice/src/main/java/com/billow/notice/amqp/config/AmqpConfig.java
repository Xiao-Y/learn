package com.billow.notice.amqp.config;

import cn.hutool.core.text.CharPool;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.billow.notice.amqp.properties.AmqpYml;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class AmqpConfig implements BeanFactoryPostProcessor {

    // 配置键前缀常量
    private static final String CONFIG_PREFIX = "notice.mq.mq-collect.";

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        try {
            // 获取环境变量
            ConfigurableEnvironment environment = beanFactory.getBean(ConfigurableEnvironment.class);

            // 解析配置文件中的 mq-collect 配置
            Map<String, AmqpYml> configMap = this.parseMqConfig(environment);

            // 注册 AmqpYml bean
            this.registerAmqpBeans(beanFactory, configMap);
        } catch (Exception e) {
            // 如果获取不到配置，说明配置还未加载完成，记录详细错误信息
            log.error("注册 AmqpYml beans 失败，错误: {}", e.getMessage(), e);
        }
    }

    /**
     * 解析配置文件中的 mq-collect 配置
     *
     * @param environment 环境变量
     * @return 解析后的配置映射
     */
    private Map<String, AmqpYml> parseMqConfig(ConfigurableEnvironment environment) {
        Map<String, AmqpYml> configMap = new HashMap<>();

        // 遍历所有配置源
        for (PropertySource<?> propertySource : environment.getPropertySources()) {
            if (propertySource instanceof MapPropertySource) {
                MapPropertySource mapPropertySource = (MapPropertySource) propertySource;

                // 遍历配置源中的所有配置项
                for (String propertyName : mapPropertySource.getPropertyNames()) {
                    if (propertyName.startsWith(CONFIG_PREFIX)) {
                        // 解析配置项，获取键和值
                        this.parseProperty(configMap, mapPropertySource, propertyName);
                    }
                }
            }
        }

        return configMap;
    }

    /**
     * 解析单个配置项
     *
     * @param configMap         配置映射
     * @param mapPropertySource 配置源
     * @param propertyName      配置项名称
     */
    private void parseProperty(Map<String, AmqpYml> configMap, MapPropertySource mapPropertySource, String propertyName) {
        // 移除前缀，获取相对配置路径
        String relativePath = propertyName.substring(CONFIG_PREFIX.length());
        // 分割配置路径，获取键和子键
        String[] parts = relativePath.split("\\.");

        if (parts.length >= 2) {
            String key = parts[0]; // mq-collect 后面的部分
            String subKey = parts[1]; // 具体的配置项

            // 确保 configMap 中有对应的 AmqpYml 对象
            AmqpYml amqpYml = configMap.computeIfAbsent(key, k -> new AmqpYml());

            // 设置 AmqpYml 对象的属性
            Object value = mapPropertySource.getProperty(propertyName);
            if (value != null) {
                this.setAmqpYmlProperty(amqpYml, subKey, value.toString());
            }
        }
    }

    /**
     * 设置 AmqpYml 对象的属性
     *
     * @param amqpYml AmqpYml 对象
     * @param subKey  子配置键
     * @param value   配置值
     */
    private void setAmqpYmlProperty(AmqpYml amqpYml, String subKey, String value) {
        try {
            // 将 kebab-case 转换为 camelCase
            String camelCaseKey = StrUtil.toCamelCase(subKey.replace(CharPool.DASHED, CharPool.UNDERLINE));

            // 使用 hutool 的 ReflectUtil 设置属性
            ReflectUtil.setFieldValue(amqpYml, camelCaseKey, value);
        } catch (Exception e) {
            // 如果设置属性失败，记录警告日志
            log.warn("设置 AmqpYml 属性失败，属性: {}, 错误: {}", subKey, e.getMessage());
        }
    }

    /**
     * 注册 AmqpYml bean
     *
     * @param beanFactory Bean 工厂
     * @param configMap   配置映射
     */
    private void registerAmqpBeans(ConfigurableListableBeanFactory beanFactory, Map<String, AmqpYml> configMap) {
        if (configMap.isEmpty()) {
            log.warn("在 configMap 中未找到 MQ 配置");
            return;
        }

        for (Map.Entry<String, AmqpYml> entry : configMap.entrySet()) {
            String configKey = entry.getKey();
            AmqpYml configValue = entry.getValue();

            // 将 kebab-case 转换为 camelCase 作为 Bean 名称
            String beanName = StrUtil.toCamelCase(configKey.replace("-", "_"));

            // 检查 Bean 是否已存在
            if (!beanFactory.containsBean(beanName)) {
                // 注册 Bean
                beanFactory.registerSingleton(beanName, configValue);
                log.info("已注册 AmqpYml bean: {}", beanName);
            } else {
                // 检查已存在的 Bean 类型
                Object existingBean = beanFactory.getBean(beanName);
                if (!(existingBean instanceof AmqpYml)) {
                    log.warn("Bean {} 已存在但不是 AmqpYml 类型，跳过注册", beanName);
                }
            }
        }
    }
}

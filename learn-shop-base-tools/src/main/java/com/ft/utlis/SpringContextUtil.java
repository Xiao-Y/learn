package com.ft.utlis;

import com.ft.exception.NullBeanException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 *
 * @author liuyongtao
 * @date 2017年4月18日 下午3:52:31
 */
@Component
public class SpringContextUtil {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringContextUtil.applicationContext = applicationContext;
    }

    /**
     * 检查applicationContext是否注入
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @return
     * @date 2017年4月18日 下午3:53:30
     */
    private static void checkApplicationContext() {
        if (applicationContext == null) {
            throw new IllegalStateException("applicaitonContext未注入,请在applicationContext.xml中定义ContextUtils");
        }
    }

    /**
     * 通过name和clazz返回指定的Bean
     */
    public static <T> T getBean(String name, Class<T> clazz) {
        checkApplicationContext();
        T bean = applicationContext.getBean(name, clazz);
        if (bean == null) {
            throw new NullBeanException(name);
        }
        return bean;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param name
     * @return
     * @date 2017年4月18日 下午4:03:54
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(String name) throws Exception {
        checkApplicationContext();
        Object bean = applicationContext.getBean(name);
        if (bean == null) {
            throw new NullBeanException(name);
        }
        return (T) bean;
    }

    /**
     * 从静态变量ApplicationContext中取得Bean, 自动转型为所赋值对象的类型.
     * <p>
     * <br>
     * added by liuyongtao<br>
     *
     * @param clazz
     * @return
     * @date 2017年4月18日 下午4:04:09
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(Class<T> clazz) throws Exception {
        checkApplicationContext();
        Map<String, T> bean = applicationContext.getBeansOfType(clazz);
        if (bean == null) {
            throw new NullBeanException(clazz);
        }
        return (T) bean;
    }
}

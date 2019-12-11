package com.billow.job.util;

import com.billow.job.exception.NullBeanException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 *
 * @author liuyongtao
 * @date 2017年4月18日 下午3:52:31
 */
public class JobContextUtil {

    private static ApplicationContext applicationContext;

    /**
     * 通过 AutowiringSpringBeanJobFactory 类实现的 ApplicationContextAware , 将其存入静态变量
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        JobContextUtil.applicationContext = applicationContext;
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
}

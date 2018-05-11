package com.ft.utlis;

import com.ft.exception.NullBeanException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 *
 * @author liuyongtao
 * @date 2017年4月18日 下午3:52:31
 */
@Component
public class BeanUtils {

    private static ApplicationContext applicationContext;

    /**
     * 实现ApplicationContextAware接口的context注入函数, 将其存入静态变量
     */
    public static void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BeanUtils.applicationContext = applicationContext;
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

    /**
     * po转vo深度复制（基本类型）
     *
     * @param po
     * @param voClass
     * @param <PO>
     * @param <VO>
     * @return
     */
    public static <PO, VO> VO convert(PO po, Class<VO> voClass) {
        VO vo = null;
        try {
            vo = voClass.newInstance();
            org.springframework.beans.BeanUtils.copyProperties(po, vo);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    /**
     * List<PO>转List<vo>深度复制（基本类型）
     *
     * @param pos
     * @param voClass
     * @param <PO>
     * @param <VO>
     * @return
     */
    public static <PO, VO> List<VO> convert(List<PO> pos, Class<VO> voClass) {
        List<VO> vos = null;
        if (ToolsUtils.isNotEmpty(pos)) {
            vos = new ArrayList<>();
            for (PO po : pos) {
                vos.add(convert(po, voClass));
            }
        }
        return vos;
    }
}

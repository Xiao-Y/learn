package com.billow.auth.utils;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 *
 * @author liuyongtao
 * @date 2017年4月18日 下午3:52:31
 */
@Component
public class ConvertUtils {

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
        if (po == null) {
            return null;
        }
        VO vo = null;
        try {
            vo = voClass.newInstance();
            BeanUtils.copyProperties(po, vo);
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
        if (!CollectionUtils.isEmpty(pos)) {
            vos = new ArrayList<>();
            for (PO po : pos) {
                vos.add(convert(po, voClass));
            }
        }
        return vos;
    }

    /**
     * 将不为空值的属性从源对象中复制到目标对象中
     *
     * @param src    : 源对象
     * @param target :目标对象
     */
    public static <SRC, TARGET> void copyNonNullProperties(SRC src, TARGET target) {
        BeanUtils.copyProperties(src, target, getNullProperties(src));
    }

    /**
     * 将为空的properties给找出来,然后返回出来
     *
     * @param src
     * @return
     */
    private static <SRC> String[] getNullProperties(SRC src) {
        BeanWrapper srcBean = new BeanWrapperImpl(src);
        PropertyDescriptor[] pds = srcBean.getPropertyDescriptors();
        Set<String> emptyName = new HashSet<>();
        for (PropertyDescriptor p : pds) {
            Object srcValue = srcBean.getPropertyValue(p.getName());
            if (srcValue == null) emptyName.add(p.getName());
        }
        String[] result = new String[emptyName.size()];
        return emptyName.toArray(result);
    }

    /**
     * List 转换为 Array
     *
     * @param list
     * @param <T>
     * @return
     */
    public static <T> T[] toArray(List<T> list, Class<T> clazz) {

        if (CollectionUtils.isEmpty(list)) {
            return (T[]) Array.newInstance(clazz, 0);
        }

        T[] t = (T[]) Array.newInstance(clazz, list.size());
        list.toArray(t);
        return t;
    }
}

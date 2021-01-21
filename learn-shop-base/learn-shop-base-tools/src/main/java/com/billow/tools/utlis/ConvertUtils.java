package com.billow.tools.utlis;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

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
     * po转vo深度复制,不复制 BasePO,BasePage（id 除外）
     *
     * @param po
     * @param voClass
     * @param <PO>
     * @param <VO>
     * @return
     */
    public static <PO, VO> VO convertIgnoreBase(PO po, Class<VO> voClass) {
        if (po == null) {
            return null;
        }
        VO vo = null;
        try {
            vo = voClass.newInstance();
            String[] ignoreProperties = {FieldUtils.CREATE_TIME, FieldUtils.CREATOR_CODE,
                    FieldUtils.UPDATER_CODE, FieldUtils.UPDATE_TIME, FieldUtils.VALID_IND
            };
            org.springframework.beans.BeanUtils.copyProperties(po, vo, ignoreProperties);
//            FieldUtils.setValue(vo, FieldUtils.REQUEST_URL, null);
            FieldUtils.setValue(vo, FieldUtils.PAGE_SIZE, null);
            FieldUtils.setValue(vo, FieldUtils.PAGE_NO, null);
            FieldUtils.setValue(vo, FieldUtils.RECORD_COUNT, null);
//            FieldUtils.setValue(vo, FieldUtils.OBJECT_ORDER_BY, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return vo;
    }

    /**
     * po转vo深度复制,不复制 BasePO,BasePage（id 除外）
     *
     * @param pos
     * @param voClass
     * @param <PO>
     * @param <VO>
     * @return
     */
    public static <PO, VO> List<VO> convertIgnoreBase(List<PO> pos, Class<VO> voClass) {
        List<VO> vos = null;
        if (ToolsUtils.isNotEmpty(pos)) {
            vos = new ArrayList<>();
            for (PO po : pos) {
                vos.add(convertIgnoreBase(po, voClass));
            }
        }
        return vos;
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
        if (po == null) {
            return null;
        }
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
     * po转vo深度复制（基本类型）
     *
     * @param po
     * @param vo
     * @param <PO>
     * @param <VO>
     * @return
     */
    public static <PO, VO> VO convert(PO po, VO vo) {
        if (po == null) {
            return vo;
        }
        if (vo == null) {
            throw new RuntimeException("VO 不能为空");
        }
        try {
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
        List<VO> vos = new ArrayList<>();
        if (ToolsUtils.isNotEmpty(pos)) {
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
            if (srcValue == null) {
                emptyName.add(p.getName());
            }
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

        if (ToolsUtils.isEmpty(list)) {
            return (T[]) Array.newInstance(clazz, 0);
        }

        T[] t = (T[]) Array.newInstance(clazz, list.size());
        list.toArray(t);
        return t;
    }
}

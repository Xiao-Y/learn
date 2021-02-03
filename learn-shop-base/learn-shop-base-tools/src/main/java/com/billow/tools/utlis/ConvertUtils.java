package com.billow.tools.utlis;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ReflectUtil;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 转换工具
 *
 * @author liuyongtao
 * @date 2017年4月18日 下午3:52:31
 */
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
                    FieldUtils.UPDATER_CODE, FieldUtils.UPDATE_TIME, FieldUtils.VALID_IND,
                    FieldUtils.PAGE_SIZE, FieldUtils.PAGE_NO, FieldUtils.RECORD_COUNT
            };
            BeanUtil.copyProperties(po, vo, ignoreProperties);
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
            BeanUtil.copyProperties(po, vo);
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
            BeanUtil.copyProperties(po, vo);
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
     * Set<PO>Set<vo>深度复制（基本类型）
     *
     * @param pos
     * @param voClass
     * @param <PO>
     * @param <VO>
     * @return
     */
    public static <PO, VO> Set<VO> convert(Set<PO> pos, Class<VO> voClass) {
        Set<VO> vos = new HashSet<>();
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
        BeanUtil.copyProperties(src, target, getNullProperties(src));
    }

    /**
     * 将为空的properties给找出来,然后返回出来
     *
     * @param src
     * @return
     */
    private static <SRC> String[] getNullProperties(SRC src) {
        Field[] fields = ReflectUtil.getFields(src.getClass());
        Set<String> emptyName = new HashSet<>();
        for (Field field : fields) {
            Object value = ReflectUtil.getFieldValue(src, field);
            if (value == null) {
                emptyName.add(field.getName());
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

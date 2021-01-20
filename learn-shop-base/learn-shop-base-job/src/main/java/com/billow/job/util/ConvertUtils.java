package com.billow.job.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyongtao
 * @create 2019-09-26 15:16
 */
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

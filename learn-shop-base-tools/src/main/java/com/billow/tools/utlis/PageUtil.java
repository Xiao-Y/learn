package com.billow.tools.utlis;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 以静态变量保存Spring ApplicationContext, 可在任何代码任何地方任何时候中取出ApplicaitonContext.
 *
 * @author liuyongtao
 * @date 2017年4月18日 下午3:52:31
 */
@Component
public class PageUtil {

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

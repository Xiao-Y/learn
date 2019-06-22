package com.billow.common.utils;

import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by billow.
 *
 * @author: bilow
 * @version: 1.0
 * @date: 2019/6/3 10:10
 * @apiNote:
 */
public class PageUtils {

//    /**
//     * List<PO>转List<vo>深度复制（基本类型）
//     *
//     * @param pos
//     * @param voClass
//     * @param <PO>
//     * @param <VO>
//     * @return
//     */
//    public static <PO, VO> Page<VO> convert(Page<PO> pos, Class<VO> voClass) {
//        Pageable pageable = new PageRequest()
//        List<PO> content = pos.getContent();
//        if(content != null){
//            List<VO> convert = ConvertUtils.convert(content, voClass);
//            Page<VO> page = new PageImpl<VO>()
//        }
//
//        return pageVo;
//    }
}

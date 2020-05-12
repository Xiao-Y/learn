package com.billow.base.workflow.utils;

import com.billow.base.workflow.vo.CustomPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 *
 * @author liuyongtao
 * @create 2019-08-28 8:27
 */
public class PageUtils {

    private static final Logger log = LoggerFactory.getLogger(PageUtils.class);

    /**
     * List 结果 封装到分页对象中
     *
     * @param pageSize 分页大小
     * @param count    总数据量
     * @param souList  原始结果集
     * @param clazz    转换到的page 泛型
     * @return com.billow.base.workflow.vo.Page<E>
     * @author LiuYongTao
     * @date 2019/8/28 8:44
     */
    public static <T, E> CustomPage<E> converListToPage(int pageSize, long count, List<T> souList, Class<E> clazz) {

        CustomPage<E> page = new CustomPage<>(pageSize, count);
        if (souList == null) {
            return page;
        }
        try {
            E tar;
            List<E> tarList = new ArrayList<>();
            for (T sou : souList) {
                tar = clazz.newInstance();
                BeanUtils.copyProperties(sou, tar);
                tarList.add(tar);
            }
            page.setTableData(tarList);
        } catch (Exception e) {
            log.error("PageUtils.converListToPage 发生了异常：", e);
        }
        return page;
    }

    /**
     * List 结果集封装到 clazz 泛型的 List 中
     *
     * @param souList
     * @param clazz
     * @return java.util.List<E>
     * @author LiuYongTao
     * @date 2019/8/29 16:44
     */
    public static <T, E> List<E> converListToList(List<T> souList, Class<E> clazz) {
        List<E> tarList = new ArrayList<>();
        if (souList == null) {
            return tarList;
        }
        try {
            E tar;
            for (T sou : souList) {
                tar = clazz.newInstance();
                BeanUtils.copyProperties(sou, tar);
                tarList.add(tar);
            }
        } catch (Exception e) {
            log.error("PageUtils.converListToPage 发生了异常：", e);
        }
        return tarList;
    }
}

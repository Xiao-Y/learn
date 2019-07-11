package com.billow.system.service;

import com.billow.system.pojo.vo.DataDictionaryVo;

import java.util.List;

/**
 * 数据字典
 *
 * @author LiuYongTao
 * @date 2019/7/11 10:57
 */
public interface DataDictionaryService {
    /**
     * 根据条件查询出数据字典数据
     *
     * @param dataDictionaryVo
     * @return java.util.List<com.billow.system.pojo.vo.DataDictionaryVo>
     * @author LiuYongTao
     * @date 2019/7/11 11:06
     */
    List<DataDictionaryVo> findDataDictionaryByCondition(DataDictionaryVo dataDictionaryVo);
}

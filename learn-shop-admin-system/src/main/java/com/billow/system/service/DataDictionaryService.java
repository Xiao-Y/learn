package com.billow.system.service;

import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.pojo.vo.DataDictionaryVo;
import org.springframework.data.domain.Page;

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

    /**
     * 根据条件查询数据字典信息
     *
     * @param dataDictionaryVo
     * @return org.springframework.data.domain.Page<com.billow.system.pojo.po.DataDictionaryPo>
     * @author LiuYongTao
     * @date 2019/11/7 9:01
     */
    Page<DataDictionaryPo> listByPage(DataDictionaryVo dataDictionaryVo);

    /**
     * 根据下拉字段分类
     *
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/11/7 9:31
     */
    List<String> findFieldType();

    /**
     * 字典下拉系统模块
     *
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/11/7 13:50
     */
    List<String> findSysModule();

    /**
     * 根据id删除
     *
     * @param id 主键id
     * @return void
     * @author LiuYongTao
     * @date 2019/11/7 9:38
     */
    void delById(Long id);

    /**
     * 保存/更新数据字典
     *
     * @param dataDictionaryVo
     * @return void
     * @author LiuYongTao
     * @date 2019/11/7 10:08
     */
    void saveOrUpdate(DataDictionaryVo dataDictionaryVo);

    /**
     * 根据id禁用数据字典
     *
     * @param id
     * @return com.billow.system.pojo.vo.DataDictionaryVo
     * @author LiuYongTao
     * @date 2019/11/7 10:46
     */
    DataDictionaryVo prohibitById(Long id);

}

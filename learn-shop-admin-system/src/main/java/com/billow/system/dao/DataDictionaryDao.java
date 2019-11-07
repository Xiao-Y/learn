package com.billow.system.dao;

import com.billow.system.pojo.po.DataDictionaryPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * 数据字典
 *
 * @author LiuYongTao
 * @date 2019/7/11 10:56
 */
public interface DataDictionaryDao extends JpaRepository<DataDictionaryPo, Long>, JpaSpecificationExecutor<DataDictionaryPo> {

    /**
     * 根据下拉字段分类
     *
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/11/7 9:33
     */
    @Query(value = "select distinct field_type from sys_data_dictionary ORDER BY field_type asc", nativeQuery = true)
    List<String> findFieldType();

    /**
     * 字典下拉系统模块
     *
     * @return java.util.List<java.lang.String>
     * @author LiuYongTao
     * @date 2019/11/7 13:50
     */
    @Query(value = "select distinct system_module from sys_data_dictionary ORDER BY system_module asc", nativeQuery = true)
    List<String> findSysModule();
}

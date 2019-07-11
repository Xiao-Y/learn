package com.billow.system.dao;

import com.billow.system.pojo.po.DataDictionaryPo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * 数据字典
 *
 * @author LiuYongTao
 * @date 2019/7/11 10:56
 */
public interface DataDictionaryDao extends JpaRepository<DataDictionaryPo, Long>, JpaSpecificationExecutor<DataDictionaryPo> {

}

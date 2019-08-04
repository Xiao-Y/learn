package com.billow.system.dao;

import com.billow.system.pojo.po.CityPo;
import com.billow.system.pojo.po.DataDictionaryPo;
import com.billow.system.pojo.vo.CityVo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

/**
 * 省市区数据字典
 *
 * @author LiuYongTao
 * @date 2019/7/11 10:56
 */
public interface CityDao extends JpaRepository<CityPo, Long>, JpaSpecificationExecutor<CityPo> {

    /**
     * 根据父级菜单id查询出子级菜单（有效）
     *
     * @param parentCityId
     * @return java.util.List<com.billow.system.pojo.vo.CityVo>
     * @author billow
     * @date 2019/8/4 13:54
     */
    List<CityPo> findByParentCityIdIsAndValidIndIsTrue(String parentCityId);
}

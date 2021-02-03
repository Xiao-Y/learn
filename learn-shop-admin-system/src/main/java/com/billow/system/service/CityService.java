package com.billow.system.service;

import com.billow.system.pojo.ex.CityEx;
import com.billow.system.pojo.po.CityPo;
import com.billow.system.pojo.vo.CityVo;

import java.util.List;
import java.util.Set;

/**
 * @author liuyongtao
 * @create 2019-08-04 13:49
 */
public interface CityService {

    /**
     * 查询省市区
     *
     * @param cityId
     * @return java.util.List<com.billow.system.pojo.ex.CityEx>
     * @author billow
     * @date 2019/8/4 16:50
     */
    CityEx findCityByParentCityId(String cityId);

    /**
     * 查询城市的下级
     *
     * @param cityId
     * @return {@link List< CityEx>}
     * @author liuyongtao
     * @since 2021-1-28 14:38
     */
    List<CityEx> getCityLowerLevel(String cityId);

    /**
     * 查询所有
     *
     * @return {@link List< CityEx>}
     * @author liuyongtao
     * @since 2021-1-28 15:16
     */
    Set<CityEx> findCity();

    /**
     * 根据城市id获取城市信息
     *
     * @param cityId
     * @return {@link CityVo}
     * @author liuyongtao
     * @since 2021-1-28 20:24
     */
    CityVo findByCityId(String cityId);
}

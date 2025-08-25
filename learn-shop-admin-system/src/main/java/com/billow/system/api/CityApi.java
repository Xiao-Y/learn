package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.system.pojo.ex.CityEx;
import com.billow.system.pojo.vo.CityVo;
import com.billow.system.service.CityService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 省市区管理
 *
 * @author liuyongtao
 * @create 2019-08-04 13:48
 */
@Slf4j
@RestController
@RequestMapping("/cityApi")
@Tag(name = "CityApi", description = "省市区管理")
public class CityApi extends BaseApi {

    @Autowired
    private CityService cityService;

    @Operation(summary = "根据城市id获取城市信息")
    @GetMapping("/findCityByCityId/{cityId}")
    public CityVo findCityById(@PathVariable("cityId") String cityId) {
        return cityService.findByCityId(cityId);
    }

    @Operation(summary = "查询城市的下级")
    @GetMapping("/getCityLowerLevel/{cityId}")
    public List<CityEx> getCityLowerLevel(@PathVariable("cityId") String cityId) {
        return cityService.getCityLowerLevel(cityId);
    }

    @Operation(summary = "查询城市本级及以下所有")
    @GetMapping("/findCityCascade/{cityId}")
    public CityEx findCityCascade(@PathVariable("cityId") String cityId) {
        return cityService.findCityByParentCityId(cityId);
    }

    @Operation(summary = "查询所有城市")
    @GetMapping("/findCity")
    public Set<CityEx> findCity() {
        return cityService.findCity();
    }
}

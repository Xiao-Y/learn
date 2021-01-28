package com.billow.system.api;

import com.billow.common.base.BaseApi;
import com.billow.common.redis.RedisUtils;
import com.billow.system.pojo.ex.CityEx;
import com.billow.system.service.CityService;
import com.billow.tools.constant.RedisCst;
import com.billow.tools.utlis.ToolsUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 省市区管理
 *
 * @author liuyongtao
 * @create 2019-08-04 13:48
 */
@Slf4j
@RestController
@RequestMapping("/cityApi")
@Api(value = "省市区管理")
public class CityApi extends BaseApi {

    @Autowired
    private CityService cityService;
    @Autowired
    private RedisUtils redisUtils;

    @ApiOperation(value = "查询所有")
    @GetMapping("/findCity")
    public Set<CityEx> findCity() {
        return cityService.findCity();
    }

    /**
     * 查询本级及以下所有
     *
     * @param cityId
     * @return {@link List< CityEx>}
     * @author liuyongtao
     * @since 2021-1-28 14:29
     */
    @ApiOperation(value = "查询本级及以下所有")
    @GetMapping("/findCity/{cityId}")
    public List<CityEx> findCity(@PathVariable("cityId") String cityId) {
        return cityService.findCityByParentCityId(cityId);
    }

    @ApiOperation(value = "查询城市的下级")
    @GetMapping("/getCityLowerLevel/{cityId}")
    public List<CityEx> getCityLowerLevel(@PathVariable("cityId") String cityId) {
        return cityService.getCityLowerLevel(cityId);
    }
}

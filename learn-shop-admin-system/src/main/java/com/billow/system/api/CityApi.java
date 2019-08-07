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

    @ApiOperation(value = "查询省市区")
    @GetMapping("/findCity/{parentCityId}")
    public List<CityEx> findCity(@PathVariable("parentCityId") String parentCityId) {
        // 从 redis 中获取
        List<CityEx> redisData = redisUtils.getArray(RedisCst.COMM_CITY, CityEx.class);
        if (ToolsUtils.isNotEmpty(redisData)) {
            return redisData;
        }
        List<CityEx> cityExes = cityService.findCityByParentCityId(parentCityId);
        // 保存到 redis 中
        redisUtils.setObj(RedisCst.COMM_CITY, cityExes);
        return cityExes;
    }
}

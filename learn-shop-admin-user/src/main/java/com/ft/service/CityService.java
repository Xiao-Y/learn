package com.ft.service;

import com.ft.model.CityModel;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

public interface CityService {

    //@ApiOperation(value = "获取city表中的信息")
    @ApiOperation(value="获取项目组Sonar对应的Url信息", notes="根据id获取项目组Sonar对应的Url信息")// 使用该注解描述接口方法信息
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "SonarUrl表ID", required = true, dataType = "Long", paramType="path")
    })
    List<CityModel> findAll(CityModel cityModel);
}

package com.ft.service.impl;

import com.ft.dao.CityRepository;
import com.ft.model.CityModel;
import com.ft.service.CityService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyongtao
 * @create 2018-02-08 10:44
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityRepository cityRepository;

    @ApiOperation(value="获取项目组Sonar对应的Url信息", notes="根据id获取项目组Sonar对应的Url信息")// 使用该注解描述接口方法信息
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "SonarUrl表ID", required = true, dataType = "Long", paramType="path")
    })
    @Override
    public List<CityModel> findAll(CityModel cityModel) {
        return cityRepository.findAll();
    }
}

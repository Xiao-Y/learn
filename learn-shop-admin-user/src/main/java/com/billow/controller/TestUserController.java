package com.billow.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.billow.common.resData.BaseResponse;

import com.billow.service.CityService;
import com.billow.service.TestService;
import com.billow.pojo.vo.CityVo;
import com.billow.pojo.vo.TestVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Api(value = "TestUserController", description = "用户操作测试")
@RestController
@RequestMapping("/testUser")
public class TestUserController {

    @Value("${words}")
    private String words;

    @Autowired
    private CityService cityService;
    @Autowired
    private TestService testService;

    @ApiOperation(value = "获取用户信息", notes = "用户信息测试")
    @GetMapping("/indexUser")
    public String indexUser(String name) {
        System.out.println(words);
        System.out.println("indexUser: " + name);
        return "indexUser:" + words + DateUtil.now();
    }

    @ApiOperation(value = "获取城市信息", notes = "查询出所有城市信息")
    @GetMapping("/findAll")
    public List<CityVo> findAll(HttpServletRequest request) {
        return cityService.findAll(null);
    }

    @ApiOperation(value = "保存用户信息", notes = "远程调用保存用户信息测试")
    @PostMapping("/saveUser")
    public BaseResponse<TestVo> saveUser(String name) {
        BaseResponse<TestVo> res = testService.saveUser(new TestVo());
        System.out.println(JSONObject.toJSONString(res));
        return res;
    }
}

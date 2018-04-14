package com.ft.controller;

import com.ft.model.CityModel;
import com.ft.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
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

    @ApiOperation(value = "获取用户信息", notes = "用户信息测试")
    @GetMapping("/indexUser")
    public String indexUser(String name) {
        System.out.println(words);
        System.out.println("indexUser: " + name);
        return "indexUser";
    }

    @ApiOperation(value = "获取城市信息", notes = "查询出所有城市信息")
    @GetMapping("/findAll")
    public List<CityModel> findAll(HttpServletRequest request) {
        return cityService.findAll(null);
    }
}

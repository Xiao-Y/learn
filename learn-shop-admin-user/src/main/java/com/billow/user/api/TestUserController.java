package com.billow.user.api;

import com.alibaba.fastjson.JSONObject;
import com.billow.tools.resData.BaseResponse;
import com.billow.user.pojo.vo.TestVo;
import com.billow.user.service.CityService;
import com.billow.user.service.TestService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "用户操作测试")
@RestController
@RequestMapping("/testUser")
public class TestUserController {

    @Value("${words}")
    private String words;

    @Autowired
    private CityService cityService;
    @Autowired
    private TestService testService;

//    @ApiOperation(value = "获取用户信息", notes = "用户信息测试")
//    @GetMapping("/indexUser")
//    @RefreshScope
//    @PreAuthorize("hasAuthority('query-demo')")
//    public String indexUser(String name) {
//        System.out.println(words);
//        System.out.println("indexUser: " + name);
//        return "indexUser:" + words + "---" + DateUtil.now();
//    }
//
//    @ApiOperation(value = "获取城市信息", notes = "查询出所有城市信息")
//    @GetMapping("/findAll")
//    @PreAuthorize("hasAuthority('sys-menuList-index')")
//    public List<CityVo> findAll(HttpServletRequest request) {
//        return cityService.findAll(null);
//    }

    @ApiOperation(value = "保存用户信息", notes = "远程调用保存用户信息测试")
    @PostMapping("/saveUser")
    public BaseResponse<TestVo> saveUser(String name) {
        BaseResponse<TestVo> res = testService.saveUser(new TestVo());
        System.out.println(JSONObject.toJSONString(res));
        return res;
    }
}

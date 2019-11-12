package com.billow.zuul.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试使用
 *
 * @author liuyongtao
 * @create 2019-11-12 11:36
 */
@RestController
public class TestApi {

    @GetMapping("/test/noauth")
    public String noAuth(){
        return "hi no auth";
    }

    @GetMapping("/testApi/auth")
    public String auth(){
        return "hi auth";
    }
}

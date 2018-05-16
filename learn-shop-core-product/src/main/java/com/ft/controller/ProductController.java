package com.ft.controller;

import com.ft.ResData.BaseResponse;
import com.ft.enums.ResCodeEnum;
import com.ft.utlis.SpringContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * 测试使用
 *
 * @author liuyongtao
 * @create 2018-05-11 11:03
 */
@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/setStringValue")
    public BaseResponse<String> setStringValue() {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        BaseResponse<String> response = new BaseResponse<>(ResCodeEnum.OK);
        opsForValue.set("name", "tom", 20, TimeUnit.SECONDS);
        try {
            ProductController productController = SpringContextUtil.getBean("productController");
            response.setResData(productController.getClass().getName());
        } catch (Exception e) {
            response.setResCode(ResCodeEnum.FAIL);
            e.printStackTrace();
        }
        return response;
    }

    @GetMapping("/getStringValue")
    public BaseResponse<String> getStringValue() {
        ValueOperations<String, String> opsForValue = redisTemplate.opsForValue();
        BaseResponse<String> response = new BaseResponse<>(ResCodeEnum.OK);
        response.setResData(opsForValue.get("name"));
        return response;
    }
}

package com.billow.product.api;

import com.alibaba.fastjson.JSONObject;
import com.billow.common.base.BaseApi;
import com.billow.tools.http.HttpUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

/**
 * 商品相关操
 *
 * @author liuyongtao
 * @create 2018-08-28 21:27
 */
@Api("商品相关操作")
@RestController
@RequestMapping("/productTestApi")
public class ProductTestApi extends BaseApi {

    @ApiOperation("test1")
    @PostMapping("/test1")
    public void findProductList() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("name", "billow");
        String json = jsonObject.toJSONString();
        String url = "http://localhost:8911/productTestApi/test2";
        String object = HttpUtils.doPost(url, json);
        System.out.println("返回值：" + object);
    }

    @ApiOperation("test2")
    @PostMapping("/test2")
    public String saveProduct() {
        Object name = request.getAttribute("name");
        System.out.println("获取值：" + name);
        try {
            InputStream inputStream = request.getInputStream();
            name = IOUtils.toString(inputStream, "UTF-8");
            System.out.println("获取值：" + name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return name + DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

}

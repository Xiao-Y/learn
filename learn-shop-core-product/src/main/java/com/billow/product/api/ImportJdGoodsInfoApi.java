package com.billow.product.api;

import com.billow.product.service.ImportJdGoodsInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 从京东爬取商品数据
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Api(tags = {"ImportJdGoodsInfoApi"}, value = "从京东爬取商品数据")
@RestController
@RequestMapping("/importJdGoodsInfoApi")
public class ImportJdGoodsInfoApi
{
    @Autowired
    private ImportJdGoodsInfo importJdGoodsInfo;

    @GetMapping("importJdGoods/{keyword}")
    public boolean importJdGoods(@PathVariable("keyword") String keyword)
    {
        return importJdGoodsInfo.importJdGoods(keyword);
    }

}

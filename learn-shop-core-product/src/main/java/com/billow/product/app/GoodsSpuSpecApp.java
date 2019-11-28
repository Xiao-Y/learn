package com.billow.product.app;

import com.billow.product.service.GoodsSpuSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * spu规格表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Api(tags = {"GoodsSpuSpecApp"}, value = "spu规格表")
@RestController
@RequestMapping("/goodsSpuSpecApp")
public class GoodsSpuSpecApp {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSpuSpecService goodsSpuSpecService;

    @ApiOperation(value = "根据 spuId 查询 spu 规格表数据")
    @GetMapping(value = "/findSpuSpec/{spuId}")
    public List<Map<String, Object>> findSpuSpec(@PathVariable String spuId) {
        return goodsSpuSpecService.findSpuSpec(spuId);
    }
}

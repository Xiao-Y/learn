package com.billow.product.app;

import com.billow.product.service.GoodsSkuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * sku表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Slf4j
@Tag(name = "GoodsSkuApp", description = "sku表")
@RestController
@RequestMapping("/goodsSkuApp")
public class GoodsSkuApp {

    @Autowired
    private GoodsSkuService goodsSkuService;

    @Operation(summary = "根据 spuId 查询 sku 规格表数据")
    @GetMapping(value = "/findSkuSpec/{spuId}")
    public List<Map<String, Object>> findSkuSpec(@PathVariable Long spuId) {
        return goodsSkuService.findSpuSpec(spuId);
    }

    @Operation(summary = "通过 spuId 获取商品 sku 信息")
    @GetMapping(value = "/findGoodsSku/{spuId}")
    public List<Map<String, Object>> findGoodsSku(@PathVariable Long spuId) {
        return goodsSkuService.findGoodsSkuSpec(spuId);
    }
}

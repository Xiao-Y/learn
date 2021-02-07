package com.billow.product.app;

import com.billow.product.producer.RefreshEsProducer;
import com.billow.product.service.GoodsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
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
 * sku表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Slf4j
@Api(tags = {"GoodsSkuApp"}, value = "sku表")
@RestController
@RequestMapping("/goodsSkuApp")
public class GoodsSkuApp {

    @Autowired
    private GoodsSkuService goodsSkuService;
    @Autowired
    private RefreshEsProducer refreshEsProducer;

    @ApiOperation(value = "根据 spuId 查询 sku 规格表数据")
    @GetMapping(value = "/findSkuSpec/{spuId}")
    public List<Map<String, Object>> findSkuSpec(@PathVariable Long spuId) {
        refreshEsProducer.sendRefreshEsInfo();
        return goodsSkuService.findSpuSpec(spuId);
    }

    @ApiOperation(value = "通过 spuId 获取商品 sku 信息")
    @GetMapping(value = "/findGoodsSku/{spuId}")
    public List<Map<String, Object>> findGoodsSku(@PathVariable Long spuId) {
        return goodsSkuService.findGoodsSkuSpec(spuId);
    }
}

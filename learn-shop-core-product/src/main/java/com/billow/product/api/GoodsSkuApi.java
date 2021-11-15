package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsSkuBuildParam;
import com.billow.product.pojo.po.GoodsSkuPo;
import com.billow.product.pojo.search.GoodsSkuSearchParam;
import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.service.GoodsSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = {"GoodsSkuApi"}, value = "sku表（stock keeping uint 库存量单位）")
@RestController
@RequestMapping("/goodsSkuApi")
public class GoodsSkuApi extends HighLevelApi<GoodsSkuService, GoodsSkuPo, GoodsSkuVo, GoodsSkuBuildParam, GoodsSkuSearchParam>
{

    @Autowired
    private GoodsSkuService goodsSkuService;

    @ApiOperation(value = "通过 spuId 获取商品 sku 信息")
    @GetMapping(value = "/findGoodsSku/{spuId}")
    public List<GoodsSkuVo> findGoodsSku(@PathVariable Long spuId) {
        return goodsSkuService.findGoodsSku(spuId);
    }

    @ApiOperation(value = "根据 spuId 查询 sku 规格表数据")
    @GetMapping(value = "/findSkuSpec/{spuId}")
    public List<Map<String, Object>> findSkuSpec(@PathVariable Long spuId) {
        return goodsSkuService.findSpuSpec(spuId);
    }

    @ApiOperation(value = "通过 spuId 获取商品 sku 信息")
    @GetMapping(value = "/findGoodsSkuSpec/{spuId}")
    public List<Map<String, Object>> findGoodsSkuSpec(@PathVariable Long spuId) {
        return goodsSkuService.findGoodsSkuSpec(spuId);
    }
}

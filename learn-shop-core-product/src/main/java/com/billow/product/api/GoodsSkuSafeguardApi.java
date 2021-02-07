package com.billow.product.api;

import com.billow.product.pojo.vo.GoodsSkuSafeguardVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.billow.product.service.GoodsSkuSafeguardService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.product.pojo.po.GoodsSkuSafeguardPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku增值保障 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 * @version v1.0
 */
@Slf4j
@Api(tags = {"GoodsSkuSafeguardApi"},value = "sku增值保障")
@RestController
@RequestMapping("/goodsSkuSafeguardApi")
public class GoodsSkuSafeguardApi {

    @Autowired
    private GoodsSkuSafeguardService goodsSkuSafeguardService;

    @ApiOperation(value = "查询分页sku增值保障数据")
    @PostMapping(value = "/findListByPage")
    public IPage<GoodsSkuSafeguardPo> findListByPage(@RequestBody GoodsSkuSafeguardVo goodsSkuSafeguardVo){
        return goodsSkuSafeguardService.findListByPage(goodsSkuSafeguardVo);
    }

    @ApiOperation(value = "根据id查询sku增值保障数据")
    @GetMapping(value = "/findById/{id}")
    public GoodsSkuSafeguardVo findById(@PathVariable("id") String id){
        GoodsSkuSafeguardPo po = goodsSkuSafeguardService.getById(id);
        return ConvertUtils.convert(po, GoodsSkuSafeguardVo.class);
    }

    @ApiOperation(value = "新增sku增值保障数据")
    @PostMapping(value = "/add")
    public GoodsSkuSafeguardVo add(@RequestBody GoodsSkuSafeguardVo goodsSkuSafeguardVo){
        GoodsSkuSafeguardPo po = ConvertUtils.convert(goodsSkuSafeguardVo, GoodsSkuSafeguardPo.class);
        goodsSkuSafeguardService.save(po);
        return ConvertUtils.convert(po, GoodsSkuSafeguardVo.class);
    }

    @ApiOperation(value = "删除sku增值保障数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return goodsSkuSafeguardService.removeById(id);
    }

    @ApiOperation(value = "更新sku增值保障数据")
    @PutMapping(value = "/update")
    public GoodsSkuSafeguardVo update(@RequestBody GoodsSkuSafeguardVo goodsSkuSafeguardVo){
        GoodsSkuSafeguardPo po = ConvertUtils.convert(goodsSkuSafeguardVo, GoodsSkuSafeguardPo.class);
        goodsSkuSafeguardService.updateById(po);
        return ConvertUtils.convert(po, GoodsSkuSafeguardVo.class);
    }

    @ApiOperation("根据ID禁用sku增值保障数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsSkuSafeguardService.prohibitById(id);
    }
}

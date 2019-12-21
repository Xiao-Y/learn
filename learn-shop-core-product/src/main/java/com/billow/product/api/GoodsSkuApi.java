package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsSkuPo;
import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.service.GoodsSkuService;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
@Api(tags = {"GoodsSkuApi"}, value = "sku表")
@RestController
@RequestMapping("/goodsSkuApi")
public class GoodsSkuApi {

    @Autowired
    private GoodsSkuService goodsSkuService;

    @ApiOperation(value = "查询分页sku表数据")
    @PostMapping(value = "/list")
    public IPage<GoodsSkuPo> findListByPage(@RequestBody GoodsSkuVo goodsSkuVo) {
        return goodsSkuService.findListByPage(goodsSkuVo);
    }

    @ApiOperation(value = "根据id查询sku表数据")
    @GetMapping(value = "/getById/{id}")
    public GoodsSkuVo getById(@PathVariable("id") String id) {
        GoodsSkuPo po = goodsSkuService.getById(id);
        return ConvertUtils.convert(po, GoodsSkuVo.class);
    }

    @ApiOperation(value = "新增sku表数据")
    @PostMapping(value = "/add")
    public GoodsSkuVo add(@RequestBody GoodsSkuVo goodsSkuVo) {
        goodsSkuService.add(goodsSkuVo);
        return goodsSkuVo;
    }

    @ApiOperation(value = "删除sku表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id) {
        return goodsSkuService.removeById(id);
    }

    @ApiOperation(value = "更新sku表数据")
    @PutMapping(value = "/update")
    public GoodsSkuVo update(@RequestBody GoodsSkuVo goodsSkuVo) {
        goodsSkuService.update(goodsSkuVo);
        return goodsSkuVo;
    }

    @ApiOperation("根据ID禁用sku表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsSkuService.prohibitById(id);
    }

    @ApiOperation(value = "通过 spuId 获取商品 sku 信息")
    @GetMapping(value = "/findGoodsSku/{spuId}")
    public List<GoodsSkuVo> findGoodsSku(@PathVariable String spuId) {
        return goodsSkuService.findGoodsSku(spuId);
    }
}

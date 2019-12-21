package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.vo.GoodsSpuSpecVo;
import com.billow.product.service.GoodsSpuSpecService;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@Slf4j
@Api(tags = {"GoodsSpuSpecApi"}, value = "spu规格表")
@RestController
@RequestMapping("/goodsSpuSpecApi")
public class GoodsSpuSpecApi {

    @Autowired
    private GoodsSpuSpecService goodsSpuSpecService;
//
//    @ApiOperation(value = "查询分页spu规格表数据")
//    @PostMapping(value = "/list")
//    public IPage<GoodsSpuSpecPo> findListByPage(@RequestBody GoodsSpuSpecVo goodsSpuSpecVo) {
//        return goodsSpuSpecService.findListByPage(goodsSpuSpecVo);
//    }
//
//    @ApiOperation(value = "根据id查询spu规格表数据")
//    @GetMapping(value = "/getById/{id}")
//    public GoodsSpuSpecVo getById(@PathVariable("id") String id) {
//        GoodsSpuSpecPo po = goodsSpuSpecService.getById(id);
//        return ConvertUtils.convert(po, GoodsSpuSpecVo.class);
//    }
//
//    @ApiOperation(value = "新增spu规格表数据")
//    @PostMapping(value = "/add")
//    public GoodsSpuSpecVo add(@RequestBody GoodsSpuSpecVo goodsSpuSpecVo) {
//        GoodsSpuSpecPo po = ConvertUtils.convert(goodsSpuSpecVo, GoodsSpuSpecPo.class);
//        goodsSpuSpecService.save(po);
//        return ConvertUtils.convert(po, GoodsSpuSpecVo.class);
//    }
//
//    @ApiOperation(value = "删除spu规格表数据")
//    @DeleteMapping(value = "/delById/{id}")
//    public boolean delById(@PathVariable("id") String id) {
//        return goodsSpuSpecService.removeById(id);
//    }
//
//    @ApiOperation(value = "更新spu规格表数据")
//    @PutMapping(value = "/update")
//    public GoodsSpuSpecVo update(@RequestBody GoodsSpuSpecVo goodsSpuSpecVo) {
//        GoodsSpuSpecPo po = ConvertUtils.convert(goodsSpuSpecVo, GoodsSpuSpecPo.class);
//        goodsSpuSpecService.updateById(po);
//        return ConvertUtils.convert(po, GoodsSpuSpecVo.class);
//    }
//
//    @ApiOperation("根据ID禁用spu规格表数据")
//    @PutMapping("/prohibitById/{id}")
//    public boolean prohibitById(@PathVariable String id) {
//        return goodsSpuSpecService.prohibitById(id);
//    }

    @ApiOperation(value = "根据 spuId 查询 spu 规格Key数据")
    @GetMapping(value = "/findSpuSpecKey/{spuId}")
    public List<String> findSpuSpecKey(@PathVariable String spuId) {
        return goodsSpuSpecService.findSpuSpecKey(spuId);
    }
}

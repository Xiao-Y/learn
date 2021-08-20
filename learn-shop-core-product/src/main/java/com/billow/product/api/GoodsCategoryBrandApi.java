package com.billow.product.api;

import com.billow.product.pojo.vo.GoodsCategoryBrandVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.billow.product.service.GoodsCategoryBrandService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.product.pojo.po.GoodsCategoryBrandPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品分类和品牌的中间表，两者是多对多关系 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 * @version v1.0
 */
@Slf4j
@Api(tags = {"GoodsCategoryBrandApi"},value = "商品分类和品牌的中间表，两者是多对多关系")
@RestController
@RequestMapping("/goodsCategoryBrandApi")
public class GoodsCategoryBrandApi {

    @Autowired
    private GoodsCategoryBrandService goodsCategoryBrandService;

    @ApiOperation(value = "查询分页商品分类和品牌的中间表，两者是多对多关系数据")
    @PostMapping(value = "/findListByPage")
    public IPage<GoodsCategoryBrandPo> findListByPage(@RequestBody GoodsCategoryBrandVo goodsCategoryBrandVo){
        return goodsCategoryBrandService.findListByPage(goodsCategoryBrandVo);
    }

    @ApiOperation(value = "根据id查询商品分类和品牌的中间表，两者是多对多关系数据")
    @GetMapping(value = "/findById/{id}")
    public GoodsCategoryBrandVo findById(@PathVariable("id") String id){
        GoodsCategoryBrandPo po = goodsCategoryBrandService.getById(id);
        return ConvertUtils.convert(po, GoodsCategoryBrandVo.class);
    }

    @ApiOperation(value = "新增商品分类和品牌的中间表，两者是多对多关系数据")
    @PostMapping(value = "/add")
    public GoodsCategoryBrandVo add(@RequestBody GoodsCategoryBrandVo goodsCategoryBrandVo){
        GoodsCategoryBrandPo po = ConvertUtils.convert(goodsCategoryBrandVo, GoodsCategoryBrandPo.class);
        goodsCategoryBrandService.save(po);
        return ConvertUtils.convert(po, GoodsCategoryBrandVo.class);
    }

    @ApiOperation(value = "删除商品分类和品牌的中间表，两者是多对多关系数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return goodsCategoryBrandService.removeById(id);
    }

    @ApiOperation(value = "更新商品分类和品牌的中间表，两者是多对多关系数据")
    @PutMapping(value = "/update")
    public GoodsCategoryBrandVo update(@RequestBody GoodsCategoryBrandVo goodsCategoryBrandVo){
        GoodsCategoryBrandPo po = ConvertUtils.convert(goodsCategoryBrandVo, GoodsCategoryBrandPo.class);
        goodsCategoryBrandService.updateById(po);
        return ConvertUtils.convert(po, GoodsCategoryBrandVo.class);
    }

    @ApiOperation("根据ID禁用商品分类和品牌的中间表，两者是多对多关系数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsCategoryBrandService.prohibitById(id);
    }
}

package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.vo.GoodsSkuSpecValueVo;
import com.billow.product.service.GoodsSkuSpecValueService;
import com.billow.tools.utlis.ConvertUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

/**
 * <p>
 * sku规格值 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-27
 * @version v1.0
 */
@Api(tags = {"GoodsSkuSpecValueApi"},value = "sku规格值")
@RestController
@RequestMapping("/goodsSkuSpecValueApi")
public class GoodsSkuSpecValueApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSkuSpecValueService goodsSkuSpecValueService;

    @ApiOperation(value = "查询分页sku规格值数据")
    @PostMapping(value = "/list")
    public IPage<GoodsSkuSpecValuePo> findListByPage(@RequestBody GoodsSkuSpecValueVo goodsSkuSpecValueVo){
        return goodsSkuSpecValueService.findListByPage(goodsSkuSpecValueVo);
    }

    @ApiOperation(value = "根据id查询sku规格值数据")
    @GetMapping(value = "/getById/{id}")
    public GoodsSkuSpecValueVo getById(@PathVariable("id") String id){
        GoodsSkuSpecValuePo po = goodsSkuSpecValueService.getById(id);
        return ConvertUtils.convert(po, GoodsSkuSpecValueVo.class);
    }

    @ApiOperation(value = "新增sku规格值数据")
    @PostMapping(value = "/add")
    public GoodsSkuSpecValueVo add(@RequestBody GoodsSkuSpecValueVo goodsSkuSpecValueVo){
        GoodsSkuSpecValuePo po = ConvertUtils.convert(goodsSkuSpecValueVo, GoodsSkuSpecValuePo.class);
        goodsSkuSpecValueService.save(po);
        return ConvertUtils.convert(po, GoodsSkuSpecValueVo.class);
    }

    @ApiOperation(value = "删除sku规格值数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return goodsSkuSpecValueService.removeById(id);
    }

    @ApiOperation(value = "更新sku规格值数据")
    @PutMapping(value = "/update")
    public GoodsSkuSpecValueVo update(@RequestBody GoodsSkuSpecValueVo goodsSkuSpecValueVo){
        GoodsSkuSpecValuePo po = ConvertUtils.convert(goodsSkuSpecValueVo, GoodsSkuSpecValuePo.class);
        goodsSkuSpecValueService.updateById(po);
        return ConvertUtils.convert(po, GoodsSkuSpecValueVo.class);
    }

    @ApiOperation("根据ID禁用sku规格值数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsSkuSpecValueService.prohibitById(id);
    }
}

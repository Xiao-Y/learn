package com.billow.product.api;

import com.billow.product.pojo.vo.GoodsSpuDetailVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.billow.product.service.GoodsSpuDetailService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.product.pojo.po.GoodsSpuDetailPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 * @version v1.0
 */
@Slf4j
@Api(tags = {"GoodsSpuDetailApi"},value = "")
@RestController
@RequestMapping("/goodsSpuDetailApi")
public class GoodsSpuDetailApi {

    @Autowired
    private GoodsSpuDetailService goodsSpuDetailService;

    @ApiOperation(value = "查询分页数据")
    @PostMapping(value = "/findListByPage")
    public IPage<GoodsSpuDetailPo> findListByPage(@RequestBody GoodsSpuDetailVo goodsSpuDetailVo){
        return goodsSpuDetailService.findListByPage(goodsSpuDetailVo);
    }

    @ApiOperation(value = "根据id查询数据")
    @GetMapping(value = "/findById/{id}")
    public GoodsSpuDetailVo findById(@PathVariable("id") String id){
        GoodsSpuDetailPo po = goodsSpuDetailService.getById(id);
        return ConvertUtils.convert(po, GoodsSpuDetailVo.class);
    }

    @ApiOperation(value = "新增数据")
    @PostMapping(value = "/add")
    public GoodsSpuDetailVo add(@RequestBody GoodsSpuDetailVo goodsSpuDetailVo){
        GoodsSpuDetailPo po = ConvertUtils.convert(goodsSpuDetailVo, GoodsSpuDetailPo.class);
        goodsSpuDetailService.save(po);
        return ConvertUtils.convert(po, GoodsSpuDetailVo.class);
    }

    @ApiOperation(value = "删除数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return goodsSpuDetailService.removeById(id);
    }

    @ApiOperation(value = "更新数据")
    @PutMapping(value = "/update")
    public GoodsSpuDetailVo update(@RequestBody GoodsSpuDetailVo goodsSpuDetailVo){
        GoodsSpuDetailPo po = ConvertUtils.convert(goodsSpuDetailVo, GoodsSpuDetailPo.class);
        goodsSpuDetailService.updateById(po);
        return ConvertUtils.convert(po, GoodsSpuDetailVo.class);
    }

    @ApiOperation("根据ID禁用数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsSpuDetailService.prohibitById(id);
    }
}

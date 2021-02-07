package com.billow.product.api;

import com.billow.product.pojo.vo.GoodsSafeguardVo;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import com.billow.product.service.GoodsSafeguardService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.product.pojo.po.GoodsSafeguardPo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 增值保障 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-02-06
 * @version v1.0
 */
@Slf4j
@Api(tags = {"GoodsSafeguardApi"},value = "增值保障")
@RestController
@RequestMapping("/goodsSafeguardApi")
public class GoodsSafeguardApi {

    @Autowired
    private GoodsSafeguardService goodsSafeguardService;

    @ApiOperation(value = "查询分页增值保障数据")
    @PostMapping(value = "/findListByPage")
    public IPage<GoodsSafeguardPo> findListByPage(@RequestBody GoodsSafeguardVo goodsSafeguardVo){
        return goodsSafeguardService.findListByPage(goodsSafeguardVo);
    }

    @ApiOperation(value = "根据id查询增值保障数据")
    @GetMapping(value = "/findById/{id}")
    public GoodsSafeguardVo findById(@PathVariable("id") String id){
        GoodsSafeguardPo po = goodsSafeguardService.getById(id);
        return ConvertUtils.convert(po, GoodsSafeguardVo.class);
    }

    @ApiOperation(value = "新增增值保障数据")
    @PostMapping(value = "/add")
    public GoodsSafeguardVo add(@RequestBody GoodsSafeguardVo goodsSafeguardVo){
        GoodsSafeguardPo po = ConvertUtils.convert(goodsSafeguardVo, GoodsSafeguardPo.class);
        goodsSafeguardService.save(po);
        return ConvertUtils.convert(po, GoodsSafeguardVo.class);
    }

    @ApiOperation(value = "删除增值保障数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return goodsSafeguardService.removeById(id);
    }

    @ApiOperation(value = "更新增值保障数据")
    @PutMapping(value = "/update")
    public GoodsSafeguardVo update(@RequestBody GoodsSafeguardVo goodsSafeguardVo){
        GoodsSafeguardPo po = ConvertUtils.convert(goodsSafeguardVo, GoodsSafeguardPo.class);
        goodsSafeguardService.updateById(po);
        return ConvertUtils.convert(po, GoodsSafeguardVo.class);
    }

    @ApiOperation("根据ID禁用增值保障数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsSafeguardService.prohibitById(id);
    }
}

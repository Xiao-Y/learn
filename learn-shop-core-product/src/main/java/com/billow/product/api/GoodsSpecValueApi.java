package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.vo.GoodsSpecValueVo;
import com.billow.product.service.GoodsSpecValueService;
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

import java.util.List;

/**
 * <p>
 * 规格值表 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-27
 * @version v1.0
 */
@Api(tags = {"GoodsSpecValueApi"},value = "规格值表")
@RestController
@RequestMapping("/goodsSpecValueApi")
public class GoodsSpecValueApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private GoodsSpecValueService goodsSpecValueService;

    @ApiOperation(value = "查询分页规格值表数据")
    @PostMapping(value = "/list")
    public IPage<GoodsSpecValuePo> findListByPage(@RequestBody GoodsSpecValueVo goodsSpecValueVo){
        return goodsSpecValueService.findListByPage(goodsSpecValueVo);
    }

    @ApiOperation(value = "根据id查询规格值表数据")
    @GetMapping(value = "/getById/{id}")
    public GoodsSpecValueVo getById(@PathVariable("id") String id){
        GoodsSpecValuePo po = goodsSpecValueService.getById(id);
        return ConvertUtils.convert(po, GoodsSpecValueVo.class);
    }

    @ApiOperation(value = "新增规格值表数据")
    @PostMapping(value = "/add")
    public GoodsSpecValueVo add(@RequestBody GoodsSpecValueVo goodsSpecValueVo){
        GoodsSpecValuePo po = ConvertUtils.convert(goodsSpecValueVo, GoodsSpecValuePo.class);
        goodsSpecValueService.save(po);
        return ConvertUtils.convert(po, GoodsSpecValueVo.class);
    }

    @ApiOperation(value = "删除规格值表数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return goodsSpecValueService.removeById(id);
    }

    @ApiOperation(value = "更新规格值表数据")
    @PutMapping(value = "/update")
    public GoodsSpecValueVo update(@RequestBody GoodsSpecValueVo goodsSpecValueVo){
        GoodsSpecValuePo po = ConvertUtils.convert(goodsSpecValueVo, GoodsSpecValuePo.class);
        goodsSpecValueService.updateById(po);
        return ConvertUtils.convert(po, GoodsSpecValueVo.class);
    }

    @ApiOperation("根据ID禁用规格值表数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return goodsSpecValueService.prohibitById(id);
    }

    @ApiOperation(value = "通过 SpecKeyId 查询出所有的规格 Value")
    @GetMapping(value = "/findListBySpecKeyId/{specKeyId}")
    public List<GoodsSpecValueVo> findListBySpecKeyId(@PathVariable("specKeyId") String specKeyId) {
        return goodsSpecValueService.findListBySpecKeyId(specKeyId);
    }
}

package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.AttributsValuePo;
import com.billow.product.pojo.vo.AttributsValueVo;
import com.billow.product.service.AttributsValueService;
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
 * 商品属性值 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 * @version v1.0
 */
@Api(tags = {"商品属性值"},value = "商品属性值")
@RestController
@RequestMapping("/attributsValueApi")
public class AttributsValueApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AttributsValueService attributsValueService;

    @ApiOperation(value = "查询分页商品属性值数据")
    @RequestMapping(value = "/list")
    public IPage<AttributsValuePo> findListByPage(@RequestBody AttributsValueVo attributsValueVo){
        return attributsValueService.findListByPage(attributsValueVo);
    }

    @ApiOperation(value = "根据id查询商品属性值数据")
    @GetMapping(value = "/getById/{id}")
    public AttributsValueVo getById(@PathVariable("id") String id){
        AttributsValuePo po = attributsValueService.getById(id);
        return ConvertUtils.convert(po, AttributsValueVo.class);
    }

    @ApiOperation(value = "新增商品属性值数据")
    @PostMapping(value = "/add")
    public AttributsValueVo add(@RequestBody AttributsValueVo attributsValueVo){
        AttributsValuePo po = ConvertUtils.convert(attributsValueVo, AttributsValuePo.class);
        attributsValueService.save(po);
        return ConvertUtils.convert(po, AttributsValueVo.class);
    }

    @ApiOperation(value = "删除商品属性值数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return attributsValueService.removeById(id);
    }

    @ApiOperation(value = "更新商品属性值数据")
    @PutMapping(value = "/update")
    public AttributsValueVo update(@RequestBody AttributsValueVo attributsValueVo){
        AttributsValuePo po = ConvertUtils.convert(attributsValueVo, AttributsValuePo.class);
        attributsValueService.updateById(po);
        return ConvertUtils.convert(po, AttributsValueVo.class);
    }

    @ApiOperation("根据ID禁用商品属性值数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return attributsValueService.prohibitById(id);
    }
}

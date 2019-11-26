package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.AttributeKeyPo;
import com.billow.product.pojo.vo.AttributeKeyVo;
import com.billow.product.service.AttributeKeyService;
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
 * 商品属性key 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 * @version v1.0
 */
@Api(tags = {"商品属性key"},value = "商品属性key")
@RestController
@RequestMapping("/attributeKeyApi")
public class AttributeKeyApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private AttributeKeyService attributeKeyService;

    @ApiOperation(value = "查询分页商品属性key数据")
    @RequestMapping(value = "/list")
    public IPage<AttributeKeyPo> findListByPage(@RequestBody AttributeKeyVo attributeKeyVo){
        return attributeKeyService.findListByPage(attributeKeyVo);
    }

    @ApiOperation(value = "根据id查询商品属性key数据")
    @GetMapping(value = "/getById/{id}")
    public AttributeKeyVo getById(@PathVariable("id") String id){
        AttributeKeyPo po = attributeKeyService.getById(id);
        return ConvertUtils.convert(po, AttributeKeyVo.class);
    }

    @ApiOperation(value = "新增商品属性key数据")
    @PostMapping(value = "/add")
    public AttributeKeyVo add(@RequestBody AttributeKeyVo attributeKeyVo){
        AttributeKeyPo po = ConvertUtils.convert(attributeKeyVo, AttributeKeyPo.class);
        attributeKeyService.save(po);
        return ConvertUtils.convert(po, AttributeKeyVo.class);
    }

    @ApiOperation(value = "删除商品属性key数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return attributeKeyService.removeById(id);
    }

    @ApiOperation(value = "更新商品属性key数据")
    @PutMapping(value = "/update")
    public AttributeKeyVo update(@RequestBody AttributeKeyVo attributeKeyVo){
        AttributeKeyPo po = ConvertUtils.convert(attributeKeyVo, AttributeKeyPo.class);
        attributeKeyService.updateById(po);
        return ConvertUtils.convert(po, AttributeKeyVo.class);
    }

    @ApiOperation("根据ID禁用商品属性key数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return attributeKeyService.prohibitById(id);
    }
}

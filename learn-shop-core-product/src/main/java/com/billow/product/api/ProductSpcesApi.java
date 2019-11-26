package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.ProductSpcesPo;
import com.billow.product.pojo.vo.ProductSpcesVo;
import com.billow.product.service.ProductSpcesService;
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
 * 商品规格 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 * @version v1.0
 */
@Api(tags = {"商品规格"},value = "商品规格")
@RestController
@RequestMapping("/productSpcesApi")
public class ProductSpcesApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductSpcesService productSpcesService;

    @ApiOperation(value = "查询分页商品规格数据")
    @RequestMapping(value = "/list")
    public IPage<ProductSpcesPo> findListByPage(@RequestBody ProductSpcesVo productSpcesVo){
        return productSpcesService.findListByPage(productSpcesVo);
    }

    @ApiOperation(value = "根据id查询商品规格数据")
    @GetMapping(value = "/getById/{id}")
    public ProductSpcesVo getById(@PathVariable("id") String id){
        ProductSpcesPo po = productSpcesService.getById(id);
        return ConvertUtils.convert(po, ProductSpcesVo.class);
    }

    @ApiOperation(value = "新增商品规格数据")
    @PostMapping(value = "/add")
    public ProductSpcesVo add(@RequestBody ProductSpcesVo productSpcesVo){
        ProductSpcesPo po = ConvertUtils.convert(productSpcesVo, ProductSpcesPo.class);
        productSpcesService.save(po);
        return ConvertUtils.convert(po, ProductSpcesVo.class);
    }

    @ApiOperation(value = "删除商品规格数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return productSpcesService.removeById(id);
    }

    @ApiOperation(value = "更新商品规格数据")
    @PutMapping(value = "/update")
    public ProductSpcesVo update(@RequestBody ProductSpcesVo productSpcesVo){
        ProductSpcesPo po = ConvertUtils.convert(productSpcesVo, ProductSpcesPo.class);
        productSpcesService.updateById(po);
        return ConvertUtils.convert(po, ProductSpcesVo.class);
    }

    @ApiOperation("根据ID禁用商品规格数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return productSpcesService.prohibitById(id);
    }
}

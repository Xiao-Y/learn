package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.product.pojo.po.ProductPo;
import com.billow.product.pojo.vo.ProductSkuVo;
import com.billow.product.pojo.vo.ProductVo;
import com.billow.product.service.ProductService;
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
 * 商品信息 前端控制器
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 * @version v1.0
 */
@Api(tags = {"商品信息"},value = "商品信息")
@RestController
@RequestMapping("/productApi")
public class ProductApi {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private ProductService productService;

    @ApiOperation(value = "查询分页商品信息数据")
    @RequestMapping(value = "/list")
    public IPage<ProductPo> findListByPage(@RequestBody ProductVo productVo){
        return productService.findListByPage(productVo);
    }

    @ApiOperation(value = "根据id查询商品信息数据")
    @GetMapping(value = "/getById/{id}")
    public ProductVo getById(@PathVariable("id") String id){
        ProductPo po = productService.getById(id);
        return ConvertUtils.convert(po, ProductVo.class);
    }

    @ApiOperation(value = "新增商品信息数据")
    @PostMapping(value = "/add")
    public ProductVo add(@RequestBody ProductVo productVo){
        ProductPo po = ConvertUtils.convert(productVo, ProductPo.class);
        productService.save(po);
        return ConvertUtils.convert(po, ProductVo.class);
    }

    @ApiOperation(value = "删除商品信息数据")
    @DeleteMapping(value = "/delById/{id}")
    public boolean delById(@PathVariable("id") String id){
        return productService.removeById(id);
    }

    @ApiOperation(value = "更新商品信息数据")
    @PutMapping(value = "/update")
    public ProductVo update(@RequestBody ProductVo productVo){
        ProductPo po = ConvertUtils.convert(productVo, ProductPo.class);
        productService.updateById(po);
        return ConvertUtils.convert(po, ProductVo.class);
    }

    @ApiOperation("根据ID禁用商品信息数据")
    @PutMapping("/prohibitById/{id}")
    public boolean prohibitById(@PathVariable String id) {
        return productService.prohibitById(id);
    }

    @ApiOperation(value = "通过 productId 获取商品 sku 信息")
    @GetMapping(value = "/findProductSku/{id}")
    public ProductSkuVo findProductSku(@PathVariable("id") String id){
        ProductSkuVo vo = productService.findProductSku(id);
        return vo;
    }
}

package com.billow.product.api;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.billow.common.base.BaseApi;
import com.billow.product.pojo.po.ProductPo;
import com.billow.product.pojo.vo.ProductImageVo;
import com.billow.product.pojo.vo.ProductVo;
import com.billow.product.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 商品相关操
 *
 * @author liuyongtao
 * @create 2018-08-28 21:27
 */
@Api("商品相关操作")
@RestController
@RequestMapping("/productApi")
public class ProductApi extends BaseApi {

    @Autowired
    private ProductService productService;

    @ApiOperation("根据条件查询商品信息")
    @PostMapping("/findProductList")
    public IPage<ProductPo> findProductList(@RequestBody ProductVo productVo) {
        IPage<ProductPo> productPos = productService.findProductList(productVo);
        return productPos;
    }

    @ApiOperation("保存商品信息")
    @PostMapping("/saveProduct")
    public ProductVo saveProduct(@RequestBody ProductVo productVo) throws Exception{
        productService.saveProduct(productVo);
        return productVo;
    }

    @ApiOperation("更新商品信息")
    @PutMapping("/updateProduct")
    public ProductVo updateProduct(@RequestBody ProductVo productVo) throws Exception {
        productService.updateProduct(productVo);
        return productVo;
    }

    @ApiOperation("根据id删除商品信息")
    @DeleteMapping("/deleteProductById/{id}")
    public ProductVo deleteProductById(@PathVariable String id) throws Exception {
        ProductVo productVo = productService.deleteProductById(id);
        return productVo;
    }

    @ApiOperation("上传商品图片，保存图片信息")
    @PostMapping("/uploadProductImage/{productId}")
    public ProductImageVo uploadProductImage(@PathVariable String productId,
                                             @RequestParam("file") MultipartFile multipartFile,
                                             ProductImageVo productImageVo) throws Exception {
        productImageVo.setProductId(productId);
        productImageVo.setOldImageName(multipartFile.getOriginalFilename());
        productImageVo.setContentType(multipartFile.getContentType());
        productImageVo.setInputStream(multipartFile.getInputStream());
        productService.uploadProductImage(productImageVo);

        productImageVo.setInputStream(null);
        return productImageVo;
    }

    @ApiOperation("通过商品id查询出商品图片")
    @GetMapping("/findProductImageByProductId/{productId}")
    public List<ProductImageVo> findProductImageByProductId(@PathVariable String productId,
                                                            ProductImageVo productImageVo) throws Exception {
        List<ProductImageVo> productImageVos = productService.findProductImageByProductId(productId, productImageVo);
        return productImageVos;
    }

    @ApiOperation("通过图片id删除商品图片")
    @DeleteMapping("/deleteProductImageById/{id}")
    public Boolean deleteProductImageById(@PathVariable String id) throws Exception {
        productService.deleteProductImageById(id);
        return true;
    }
}

package com.billow.api;

import cn.hutool.core.io.FileUtil;
import com.billow.common.base.BaseApi;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.po.product.ProductPo;
import com.billow.pojo.vo.product.ProductImageVo;
import com.billow.pojo.vo.product.ProductVo;
import com.billow.service.ProductService;
import com.billow.tools.generator.SequenceUtil;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.FieldUtils;
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
    public BaseResponse<Page<ProductPo>> findProductList(@RequestBody ProductVo productVo) {
        BaseResponse<Page<ProductPo>> baseResponse = super.getBaseResponse();
        try {
            Page<ProductPo> productPos = productService.findProductList(productVo);
            baseResponse.setResData(productPos);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }

    @ApiOperation("保存商品信息")
    @PostMapping("/saveProduct")
    public BaseResponse<ProductVo> saveProduct(@RequestBody ProductVo productVo) {
        BaseResponse<ProductVo> baseResponse = super.getBaseResponse();
        try {
            String userCode = super.findUserCode();
            productService.saveProduct(productVo, userCode);
            baseResponse.setResData(productVo);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }

    @ApiOperation("更新商品信息")
    @PutMapping("/updateProduct")
    public BaseResponse<ProductVo> updateProduct(@RequestBody ProductVo productVo) {
        BaseResponse<ProductVo> baseResponse = super.getBaseResponse();
        try {
            String userCode = super.findUserCode();
            productService.updateProduct(productVo, userCode);
            baseResponse.setResData(productVo);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }

    @ApiOperation("根据id删除商品信息")
    @DeleteMapping("/deleteProductById/{id}")
    public BaseResponse<ProductVo> deleteProductById(@PathVariable String id) {
        BaseResponse<ProductVo> baseResponse = super.getBaseResponse();
        try {
            String userCode = super.findUserCode();
            ProductVo productVo = productService.deleteProductById(id, userCode);
            baseResponse.setResData(productVo);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }

    @ApiOperation("上传商品图片，保存图片信息")
    @PostMapping("/uploadProductImage/{productId}")
    public BaseResponse<ProductImageVo> uploadProductImage(@PathVariable String productId,
                                                           @RequestParam("file") MultipartFile multipartFile,
                                                           ProductImageVo productImageVo) throws Exception {
        BaseResponse<ProductImageVo> baseResponse = super.getBaseResponse();
        try {
            String userCode = super.findUserCode();
            productImageVo.setProductId(productId);
            productImageVo.setOldImageName(multipartFile.getOriginalFilename());
            productImageVo.setContentType(multipartFile.getContentType());
            productImageVo.setInputStream(multipartFile.getInputStream());
            productService.uploadProductImage(productImageVo, userCode);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        productImageVo.setInputStream(null);
        return baseResponse;
    }

    @ApiOperation("通过商品id查询出商品图片")
    @GetMapping("/findProductImageByProductId/{productId}")
    public BaseResponse<List<ProductImageVo>> findProductImageByProductId(@PathVariable String productId,
                                                                          ProductImageVo productImageVo) throws Exception {
        BaseResponse<List<ProductImageVo>> baseResponse = super.getBaseResponse();
        try {
            List<ProductImageVo> productImageVos = productService.findProductImageByProductId(productId, productImageVo);
            baseResponse.setResData(productImageVos);
        } catch (Exception e) {
            this.getErrorInfo(e, baseResponse);
        }
        return baseResponse;
    }
}

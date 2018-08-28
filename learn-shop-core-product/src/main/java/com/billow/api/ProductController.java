package com.billow.api;

import com.billow.common.base.BaseApi;
import com.billow.common.resData.BaseResponse;
import com.billow.pojo.po.product.ProductPo;
import com.billow.pojo.vo.product.ProductVo;
import com.billow.service.ProductService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 商品相关操
 *
 * @author liuyongtao
 * @create 2018-08-28 21:27
 */
@Api("商品相关操作")
@RestController
@RequestMapping("/product")
public class ProductController extends BaseApi {

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
}

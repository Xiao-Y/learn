package com.billow.app.controller;

import com.billow.app.feign.product.GoodsSkuFeign;
import com.billow.tools.enums.ResCodeEnum;
import com.billow.tools.exception.GlobalException;
import com.billow.tools.resData.BaseResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * <p>
 * sku表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Slf4j
@Api(tags = {"GoodsSkuApp"}, value = "sku表")
@RestController
@RequestMapping("/goodsSkuApp")
public class GoodsSkuApp {

    @Autowired
    private GoodsSkuFeign goodsSkuFeign;

    @ApiOperation(value = "根据 spuId 查询 sku 规格表数据")
    @GetMapping(value = "/findSkuSpec/{spuId}")
    public BaseResponse<List<Map<String, Object>>> findSkuSpec(@PathVariable Long spuId) {
        BaseResponse<List<Map<String, Object>>> baseResponse = goodsSkuFeign.findSkuSpec(spuId);
        if (!Objects.equals(baseResponse.getResCode(), ResCodeEnum.OK)) {
            throw new GlobalException(ResCodeEnum.RESCODE_OTHER_ERROR);
        }
        return baseResponse;
    }

    @ApiOperation(value = "通过 spuId 获取商品 sku 信息")
    @GetMapping(value = "/findGoodsSku/{spuId}")
    public BaseResponse<List<Map<String, Object>>> findGoodsSku(@PathVariable Long spuId) {
        BaseResponse<List<Map<String, Object>>> baseResponse = goodsSkuFeign.findGoodsSkuSpec(spuId);
        if (!Objects.equals(baseResponse.getResCode(), ResCodeEnum.OK)) {
            throw new GlobalException(ResCodeEnum.RESCODE_OTHER_ERROR);
        }
        return baseResponse;
    }
}

package com.billow.app.controller;

import com.billow.app.feign.product.GoodsSpuFeign;
import com.billow.product.interfaces.vo.GoodsSpuVo;
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

import java.util.Objects;

/**
 * <p>
 * 商品相关 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Slf4j
@Api(tags = {"GoodsSpuApp"}, value = "spu表")
@RestController
@RequestMapping("/goodsSpuApp")
public class GoodsSpuApp {

    @Autowired
    private GoodsSpuFeign goodsSpuFeign;

    @ApiOperation(value = "根据id查询spu表数据")
    @GetMapping(value = "/getById/{id}")
    public BaseResponse<GoodsSpuVo> getById(@PathVariable("id") Long id) {
        BaseResponse<GoodsSpuVo> baseResponse = goodsSpuFeign.getById(id);
        if (!Objects.equals(baseResponse.getResCode(), ResCodeEnum.OK)) {
            throw new GlobalException(ResCodeEnum.RESCODE_OTHER_ERROR);
        }
        return baseResponse;
    }
}

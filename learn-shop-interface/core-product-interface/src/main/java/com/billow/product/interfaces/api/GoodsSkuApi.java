package com.billow.product.interfaces.api;

import com.billow.product.interfaces.constant.ContextPath;
import com.billow.tools.resData.BaseResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(ContextPath.CORE_PRODUCT +"/goodsSkuApi")
public interface GoodsSkuApi {

    /**
     * 根据 spuId 查询 sku 规格表数据
     *
     * @param spuId
     * @return {@link List<Map<String,Object>>}
     * @author liuyongtao
     * @since 2021-9-7 14:18
     */
    @GetMapping(value = "/findSkuSpec/{spuId}")
    BaseResponse<List<Map<String, Object>>> findSkuSpec(@PathVariable Long spuId);

    /**
     * 通过 spuId 获取商品 sku 信息
     *
     * @param spuId
     * @return {@link List<Map<String,Object>>}
     * @author liuyongtao
     * @since 2021-9-7 14:18
     */
    @GetMapping(value = "/findGoodsSkuSpec/{spuId}")
    BaseResponse<List<Map<String, Object>>> findGoodsSkuSpec(@PathVariable Long spuId);
}
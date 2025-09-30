package com.billow.product.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.search.GoodsSpuSpecSearchParam;
import com.billow.product.service.GoodsSpuSpecService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * spu规格表 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "GoodsSpuSpecApi", description = "spu规格表")
@RestController
@RequestMapping("/goodsSpuSpecApi")
public class GoodsSpuSpecApi extends HighLevelV2Api<GoodsSpuSpecService, GoodsSpuSpecPo, GoodsSpuSpecSearchParam> {

    @Autowired
    private GoodsSpuSpecService goodsSpuSpecService;

    @Operation(summary = "根据 spuId 查询 spu 规格Key数据")
    @GetMapping(value = "/findSpuSpecKey/{spuId}")
    public List<Long> findSpuSpecKey(@PathVariable Long spuId) {
        return goodsSpuSpecService.findSpuSpecKey(spuId);
    }
}

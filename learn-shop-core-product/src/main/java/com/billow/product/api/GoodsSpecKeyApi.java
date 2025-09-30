package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.search.GoodsSpecKeySearchParam;
import com.billow.product.pojo.vo.GoodsSpecKeyVo;
import com.billow.product.service.GoodsSpecKeyService;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 规格表 前端控制器
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Slf4j
@Tag(name = "GoodsSpecKeyApi", description = "规格表")
@RestController
@RequestMapping("/goodsSpecKeyApi")
public class GoodsSpecKeyApi extends HighLevelApi<GoodsSpecKeyService, GoodsSpecKeyPo, GoodsSpecKeySearchParam> {

    @Autowired
    private GoodsSpecKeyService goodsSpecKeyService;

    @Operation(summary = "通过 CategoryId 查询出所有的规格 KEY")
    @GetMapping(value = "/findListByCategoryId/{categoryId}")
    public List<GoodsSpecKeyPo> findListByCategoryId(@PathVariable("categoryId") Long categoryId) {
        return goodsSpecKeyService.findListByCategoryId(categoryId);
    }

    @Operation(summary = "保存一组规格信息")
    @PostMapping(value = "/saveList")
    public List<GoodsSpecKeyVo> saveList(@RequestBody List<GoodsSpecKeyVo> goodsSpecKeyVos) {
        return goodsSpecKeyService.saveList(goodsSpecKeyVos);
    }
}

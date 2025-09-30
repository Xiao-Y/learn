package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.search.GoodsSpuSearchParam;
import com.billow.product.service.GoodsSpuService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * spu表（Standard Product Unit, 标准产品单元） 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "GoodsSpuApi", description = "spu")
@RestController
@RequestMapping("/goodsSpuApi")
public class GoodsSpuApi extends HighLevelApi<GoodsSpuService, GoodsSpuPo, GoodsSpuSearchParam> {

    @Autowired
    private GoodsSpuService goodsSpuService;
//
//    @Operation(summary = "异步导出商品SPU数据")
//    @GetMapping("/export")
//    public String asyncExport(HttpServletResponse response) {
//        return goodsSpuService.asyncExport(response);
//    }
//
//    @Operation(summary = "异步导入商品SPU数据")
//    @PostMapping("/import")
//    public String asyncImport(@RequestParam("file") MultipartFile file) throws ExecutionException, InterruptedException {
//        return goodsSpuService.asyncImport(file);
//    }
}

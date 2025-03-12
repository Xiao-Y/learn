package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsSpuBuildParam;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.search.GoodsSpuSearchParam;
import com.billow.product.pojo.vo.GoodsSpuVo;
import com.billow.product.service.GoodsSpuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ExecutionException;

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
@Api(tags = {"GoodsSpuApi"}, value = "spu")
@RestController
@RequestMapping("/goodsSpuApi")
public class GoodsSpuApi extends HighLevelApi<GoodsSpuService, GoodsSpuPo, GoodsSpuVo, GoodsSpuBuildParam, GoodsSpuSearchParam> {

    @Autowired
    private GoodsSpuService goodsSpuService;

    @ApiOperation("异步导出商品SPU数据")
    @GetMapping("/export")
    public String asyncExport(HttpServletResponse response) {
        return goodsSpuService.asyncExport(response);
    }

    @ApiOperation("异步导入商品SPU数据")
    @PostMapping("/import")
    public String asyncImport(@RequestParam("file") MultipartFile file) throws ExecutionException, InterruptedException {
        return goodsSpuService.asyncImport(file);
    }
}

package com.billow.product.api;

import com.billow.product.pojo.build.GoodsSkuSpecValueBuildParam;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.search.GoodsSkuSpecValueSearchParam;
import com.billow.product.pojo.vo.GoodsSkuSpecValueVo;
import com.billow.product.service.GoodsSkuSpecValueService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku规格值 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"GoodsSkuSpecValueApi"},value = "sku规格值")
@RestController
@RequestMapping("/goodsSkuSpecValueApi")
public class GoodsSkuSpecValueApi extends HighLevelApi<GoodsSkuSpecValueService, GoodsSkuSpecValuePo, GoodsSkuSpecValueVo, GoodsSkuSpecValueBuildParam, GoodsSkuSpecValueSearchParam> {

}

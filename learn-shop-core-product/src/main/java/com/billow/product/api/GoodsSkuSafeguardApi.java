package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsSkuSafeguardBuildParam;
import com.billow.product.pojo.po.GoodsSkuSafeguardPo;
import com.billow.product.pojo.search.GoodsSkuSafeguardSearchParam;
import com.billow.product.pojo.vo.GoodsSkuSafeguardVo;
import com.billow.product.service.GoodsSkuSafeguardService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku增值保障 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"GoodsSkuSafeguardApi"},value = "sku增值保障")
@RestController
@RequestMapping("/goodsSkuSafeguardApi")
public class GoodsSkuSafeguardApi extends HighLevelApi<GoodsSkuSafeguardService, GoodsSkuSafeguardPo, GoodsSkuSafeguardVo, GoodsSkuSafeguardBuildParam, GoodsSkuSafeguardSearchParam>
{

}

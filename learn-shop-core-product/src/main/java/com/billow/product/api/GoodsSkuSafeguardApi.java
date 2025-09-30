package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.po.GoodsSkuSafeguardPo;
import com.billow.product.pojo.search.GoodsSkuSafeguardSearchParam;
import com.billow.product.service.GoodsSkuSafeguardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku增值保障 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "GoodsSkuSafeguardApi", description = "sku增值保障")
@RestController
@RequestMapping("/goodsSkuSafeguardApi")
public class GoodsSkuSafeguardApi extends HighLevelApi<GoodsSkuSafeguardService, GoodsSkuSafeguardPo, GoodsSkuSafeguardSearchParam> {

}

package com.billow.product.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.search.GoodsSkuSpecValueSearchParam;
import com.billow.product.service.GoodsSkuSpecValueService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku规格值 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "GoodsSkuSpecValueApi", description = "sku规格值")
@RestController
@RequestMapping("/goodsSkuSpecValueApi")
public class GoodsSkuSpecValueApi extends HighLevelV2Api<GoodsSkuSpecValueService, GoodsSkuSpecValuePo, GoodsSkuSpecValueSearchParam> {

}

package com.billow.product.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.product.pojo.po.GoodsVertifyRecordPo;
import com.billow.product.pojo.search.GoodsVertifyRecordSearchParam;
import com.billow.product.service.GoodsVertifyRecordService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品审核记录表，用于记录商品审核记录 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "GoodsVertifyRecordApi", description = "商品审核记录表")
@RestController
@RequestMapping("/goodsVertifyRecordApi")
public class GoodsVertifyRecordApi extends HighLevelV2Api<GoodsVertifyRecordService, GoodsVertifyRecordPo, GoodsVertifyRecordSearchParam> {

}

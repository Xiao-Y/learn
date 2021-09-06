package com.billow.product.api;

import com.billow.product.pojo.build.GoodsVertifyRecordBuildParam;
import com.billow.product.pojo.vo.GoodsVertifyRecordVo;
import com.billow.product.pojo.search.GoodsVertifyRecordSearchParam;
import com.billow.product.pojo.po.GoodsVertifyRecordPo;
import com.billow.product.service.GoodsVertifyRecordService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品审核记录表，用于记录商品审核记录 前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 * @version v2.0
 */
@Slf4j
@Api(tags = {"GoodsVertifyRecordApi"},value = "商品审核记录表，用于记录商品审核记录")
@RestController
@RequestMapping("/goodsVertifyRecordApi")
public class GoodsVertifyRecordApi extends HighLevelApi<GoodsVertifyRecordService, GoodsVertifyRecordPo, GoodsVertifyRecordVo, GoodsVertifyRecordBuildParam, GoodsVertifyRecordSearchParam> {

}

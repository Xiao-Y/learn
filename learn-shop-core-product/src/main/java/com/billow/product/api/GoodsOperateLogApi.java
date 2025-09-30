package com.billow.product.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.product.pojo.po.GoodsOperateLogPo;
import com.billow.product.pojo.search.GoodsOperateLogSearchParam;
import com.billow.product.service.GoodsOperateLogService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品操作记录表，用于记录商品操作记录
 * <p>
 * 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Slf4j
@Tag(name = "GoodsOperateLogApi", description = "商品操作记录表")
@RestController
@RequestMapping("/goodsOperateLogApi")
public class GoodsOperateLogApi extends HighLevelV2Api<GoodsOperateLogService, GoodsOperateLogPo, GoodsOperateLogSearchParam> {

}

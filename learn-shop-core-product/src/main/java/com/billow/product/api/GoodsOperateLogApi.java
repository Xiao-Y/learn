package com.billow.product.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.product.pojo.build.GoodsOperateLogBuildParam;
import com.billow.product.pojo.po.GoodsOperateLogPo;
import com.billow.product.pojo.search.GoodsOperateLogSearchParam;
import com.billow.product.pojo.vo.GoodsOperateLogVo;
import com.billow.product.service.GoodsOperateLogService;
import io.swagger.annotations.Api;
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
@Api(tags = {"GoodsOperateLogApi"}, value = "商品操作记录表，用于记录商品操作记录")
@RestController
@RequestMapping("/goodsOperateLogApi")
public class GoodsOperateLogApi extends HighLevelApi<GoodsOperateLogService, GoodsOperateLogPo, GoodsOperateLogVo, GoodsOperateLogBuildParam, GoodsOperateLogSearchParam>
{

}

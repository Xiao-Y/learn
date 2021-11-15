package com.billow.order.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.order.pojo.build.OrderOperateHistoryBuildParam;
import com.billow.order.pojo.po.OrderOperateHistoryPo;
import com.billow.order.pojo.search.OrderOperateHistorySearchParam;
import com.billow.order.pojo.vo.OrderOperateHistoryVo;
import com.billow.order.service.OrderOperateHistoryService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author billow
 * @since 2021-08-23
 * @version v2.0
 */
@Slf4j
@Api(tags = {"OrderOperateHistoryApi"},value = "")
@RestController
@RequestMapping("/orderOperateHistoryApi")
public class OrderOperateHistoryApi extends HighLevelApi<OrderOperateHistoryService, OrderOperateHistoryPo, OrderOperateHistoryVo, OrderOperateHistoryBuildParam, OrderOperateHistorySearchParam>
{

}

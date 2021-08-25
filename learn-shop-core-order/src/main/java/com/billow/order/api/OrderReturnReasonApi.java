package com.billow.order.api;

import com.billow.order.api.HighLevelApi;
import com.billow.order.pojo.build.OrderReturnReasonBuildParam;
import com.billow.order.pojo.po.OrderReturnReasonPo;
import com.billow.order.pojo.search.OrderReturnReasonSearchParam;
import com.billow.order.pojo.vo.OrderReturnReasonVo;
import com.billow.order.service.OrderReturnReasonService;
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
@Api(tags = {"OrderReturnReasonApi"},value = "")
@RestController
@RequestMapping("/orderReturnReasonApi")
public class OrderReturnReasonApi extends HighLevelApi<OrderReturnReasonService, OrderReturnReasonPo, OrderReturnReasonVo, OrderReturnReasonBuildParam, OrderReturnReasonSearchParam> {

}

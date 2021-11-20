package com.billow.order.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.order.pojo.build.OrderReturnApplyBuildParam;
import com.billow.order.pojo.po.OrderReturnApplyPo;
import com.billow.order.pojo.search.OrderReturnApplySearchParam;
import com.billow.order.pojo.vo.OrderReturnApplyVo;
import com.billow.order.service.OrderReturnApplyService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@Slf4j
@Api(tags = {"OrderReturnApplyApi"}, value = "")
@RestController
@RequestMapping("/orderReturnApplyApi")
public class OrderReturnApplyApi extends HighLevelApi<OrderReturnApplyService, OrderReturnApplyPo, OrderReturnApplyVo, OrderReturnApplyBuildParam, OrderReturnApplySearchParam> {

}

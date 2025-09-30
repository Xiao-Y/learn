package com.billow.order.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.order.pojo.build.OrderReturnApplyBuildParam;
import com.billow.order.pojo.po.OrderReturnApplyPo;
import com.billow.order.pojo.search.OrderReturnApplySearchParam;
import com.billow.order.pojo.vo.OrderReturnApplyVo;
import com.billow.order.service.OrderReturnApplyService;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "OrderReturnApplyApi", description = "")
@RestController
@RequestMapping("/orderReturnApplyApi")
public class OrderReturnApplyApi extends HighLevelV2Api<OrderReturnApplyService, OrderReturnApplyPo, OrderReturnApplySearchParam> {

}

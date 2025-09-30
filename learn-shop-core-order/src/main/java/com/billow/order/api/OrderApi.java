package com.billow.order.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.order.pojo.build.OrderBuildParam;
import com.billow.order.pojo.po.OrderPo;
import com.billow.order.pojo.search.OrderSearchParam;
import com.billow.order.pojo.vo.OrderVo;
import com.billow.order.service.OrderService;
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
@Tag(name = "OrderApi", description = "")
@RestController
@RequestMapping("/orderApi")
public class OrderApi extends HighLevelV2Api<OrderService, OrderPo, OrderSearchParam> {

}

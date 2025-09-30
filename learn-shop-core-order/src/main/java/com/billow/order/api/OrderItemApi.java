package com.billow.order.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.order.pojo.po.OrderItemPo;
import com.billow.order.pojo.search.OrderItemSearchParam;
import com.billow.order.service.OrderItemService;
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
@Tag(name = "OrderItemApi", description = "")
@RestController
@RequestMapping("/orderItemApi")
public class OrderItemApi extends HighLevelApi<OrderItemService, OrderItemPo, OrderItemSearchParam> {

}

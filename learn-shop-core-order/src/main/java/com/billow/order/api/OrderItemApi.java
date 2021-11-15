package com.billow.order.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.order.pojo.build.OrderItemBuildParam;
import com.billow.order.pojo.po.OrderItemPo;
import com.billow.order.pojo.search.OrderItemSearchParam;
import com.billow.order.pojo.vo.OrderItemVo;
import com.billow.order.service.OrderItemService;
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
@Api(tags = {"OrderItemApi"},value = "")
@RestController
@RequestMapping("/orderItemApi")
public class OrderItemApi extends HighLevelApi<OrderItemService, OrderItemPo, OrderItemVo, OrderItemBuildParam, OrderItemSearchParam>
{

}

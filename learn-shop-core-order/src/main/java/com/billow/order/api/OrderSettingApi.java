package com.billow.order.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.order.pojo.po.OrderSettingPo;
import com.billow.order.pojo.search.OrderSettingSearchParam;
import com.billow.order.service.OrderSettingService;
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
@Tag(name = "OrderSettingApi", description = "")
@RestController
@RequestMapping("/orderSettingApi")
public class OrderSettingApi extends HighLevelApi<OrderSettingService, OrderSettingPo, OrderSettingSearchParam> {

}

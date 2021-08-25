package com.billow.order.api;

import com.billow.order.api.HighLevelApi;
import com.billow.order.pojo.build.OrderSettingBuildParam;
import com.billow.order.pojo.po.OrderSettingPo;
import com.billow.order.pojo.search.OrderSettingSearchParam;
import com.billow.order.pojo.vo.OrderSettingVo;
import com.billow.order.service.OrderSettingService;
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
@Api(tags = {"OrderSettingApi"},value = "")
@RestController
@RequestMapping("/orderSettingApi")
public class OrderSettingApi extends HighLevelApi<OrderSettingService, OrderSettingPo, OrderSettingVo, OrderSettingBuildParam, OrderSettingSearchParam> {

}

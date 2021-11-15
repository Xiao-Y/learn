package com.billow.order.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.order.pojo.build.CartItemBuildParam;
import com.billow.order.pojo.po.CartItemPo;
import com.billow.order.pojo.search.CartItemSearchParam;
import com.billow.order.pojo.vo.CartItemVo;
import com.billow.order.service.CartItemService;
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
@Api(tags = {"CartItemApi"},value = "")
@RestController
@RequestMapping("/cartItemApi")
public class CartItemApi extends HighLevelApi<CartItemService, CartItemPo, CartItemVo, CartItemBuildParam, CartItemSearchParam>
{

}

package com.billow.order.api;

import com.billow.mybatis.base.HighLevelV2Api;
import com.billow.order.pojo.build.CartItemBuildParam;
import com.billow.order.pojo.po.CartItemPo;
import com.billow.order.pojo.search.CartItemSearchParam;
import com.billow.order.pojo.vo.CartItemVo;
import com.billow.order.service.CartItemService;
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
@Tag(name = "CartItemApi", description = "")
@RestController
@RequestMapping("/cartItemApi")
public class CartItemApi extends HighLevelV2Api<CartItemService, CartItemPo, CartItemSearchParam> {

}

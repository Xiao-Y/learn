package com.billow.cart.api;

import com.billow.mybatis.base.HighLevelApi;
import com.billow.cart.pojo.build.CartBuildParam;
import com.billow.cart.pojo.po.CartPo;
import com.billow.cart.pojo.search.CartSearchParam;
import com.billow.cart.pojo.vo.CartVo;
import com.billow.cart.service.CartService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 购物车接口
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Slf4j
@Api(tags = {"CartApi"}, value = "购物车")
@RestController
@RequestMapping("/cartApi")
public class CartApi extends HighLevelApi<CartService, CartPo, CartVo, CartBuildParam, CartSearchParam> {

} 
package com.billow.cart.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.cart.pojo.po.CartPo;
import com.billow.cart.pojo.search.CartSearchParam;
import com.billow.cart.pojo.vo.CartVo;
import com.billow.cart.pojo.vo.CartItemVo;

import java.util.List;

/**
 * 购物车服务接口
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
public interface CartService extends HighLevelService<CartPo, CartSearchParam> {

    /**
     * 添加商品到购物车
     *
     * @param skuId    SKU ID
     * @param quantity 数量
     * @return 购物车信息
     */
    CartVo addToCart(Long skuId, Integer quantity);

    /**
     * 更新购物车商品数量
     *
     * @param skuId    SKU ID
     * @param quantity 数量
     * @return 是否成功
     */
    Boolean updateQuantity(Long skuId, Integer quantity);

    /**
     * 删除购物车商品
     *
     * @param skuIds SKU ID列表
     * @return 是否成功
     */
    Boolean deleteItems(List<Long> skuIds);

    /**
     * 清空购物车
     *
     * @return 是否成功
     */
    Boolean clearCart();

    /**
     * 选择/取消选择商品
     *
     * @param skuIds   SKU ID列表
     * @param selected 是否选中
     * @return 是否成功
     */
    Boolean selectItems(List<Long> skuIds, Boolean selected);

    /**
     * 获取购物车列表
     *
     * @return 购物车信息
     */
    CartVo getCartList();

    /**
     * 获取结算商品信息
     *
     * @return 结算商品列表
     */
    List<CartItemVo> getSettlementItems();
} 
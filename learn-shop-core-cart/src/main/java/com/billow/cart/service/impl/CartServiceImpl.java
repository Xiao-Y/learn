package com.billow.cart.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.billow.common.utils.UserTools;
import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.cart.dao.CartDao;
import com.billow.cart.pojo.po.CartPo;
import com.billow.cart.pojo.search.CartSearchParam;
import com.billow.cart.pojo.vo.CartVo;
import com.billow.cart.pojo.vo.CartItemVo;
import com.billow.cart.service.CartService;
import com.billow.tools.exception.GlobalException;
import com.billow.tools.enums.ResCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 购物车服务实现类
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Slf4j
@Service
public class CartServiceImpl extends HighLevelServiceImpl<CartDao, CartPo, CartSearchParam> implements CartService {

    @Autowired
    private CartDao cartDao;
    
    @Autowired
    private UserTools userTools;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartVo addToCart(Long skuId, Integer quantity) {
        // 获取当前用户ID
        Long userId = Long.parseLong(userTools.getCurrentUserCode());
        
        // 查询购物车是否已存在该商品
        QueryWrapper<CartPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("sku_id", skuId);
        List<CartPo> existingItems = this.list(queryWrapper);
        
        if (!existingItems.isEmpty()) {
            // 更新数量
            CartPo existingItem = existingItems.get(0);
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
            this.updateById(existingItem);
        } else {
            // 新增购物车项
            CartPo cartItem = new CartPo();
            cartItem.setUserId(userId);
            cartItem.setSkuId(skuId);
            cartItem.setQuantity(quantity);
            cartItem.setSelected(true);
            this.save(cartItem);
        }
        
        return getCartList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateQuantity(Long skuId, Integer quantity) {
        if (quantity <= 0) {
            throw new GlobalException(ResCodeEnum.RESCODE_RULE_UNMATCH);
        }
        
        Long userId = Long.parseLong(userTools.getCurrentUserCode());
        QueryWrapper<CartPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("sku_id", skuId);
        List<CartPo> items = this.list(queryWrapper);
        
        if (items.isEmpty()) {
            throw new GlobalException(ResCodeEnum.RESCODE_NULL_RESULT);
        }
        
        CartPo item = items.get(0);
        item.setQuantity(quantity);
        return this.updateById(item);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean deleteItems(List<Long> skuIds) {
        Long userId = Long.parseLong(userTools.getCurrentUserCode());
        QueryWrapper<CartPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .in("sku_id", skuIds);
        return this.remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean clearCart() {
        Long userId = Long.parseLong(userTools.getCurrentUserCode());
        QueryWrapper<CartPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        return this.remove(queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean selectItems(List<Long> skuIds, Boolean selected) {
        Long userId = Long.parseLong(userTools.getCurrentUserCode());
        QueryWrapper<CartPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .in("sku_id", skuIds);
        List<CartPo> items = this.list(queryWrapper);
        
        items.forEach(item -> item.setSelected(selected));
        return this.updateBatchById(items);
    }

    @Override
    public CartVo getCartList() {
        Long userId = Long.parseLong(userTools.getCurrentUserCode());
        QueryWrapper<CartPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId);
        List<CartPo> items = this.list(queryWrapper);
        
        // TODO: 调用商品服务获取商品详情
        // TODO: 转换为CartVo对象
        return null;
    }

    @Override
    public List<CartItemVo> getSettlementItems() {
        Long userId = Long.parseLong(userTools.getCurrentUserCode());
        QueryWrapper<CartPo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("user_id", userId)
                   .eq("selected", true);
        List<CartPo> items = this.list(queryWrapper);
        
        // TODO: 调用商品服务获取商品详情
        // TODO: 转换为CartItemVo对象
        return null;
    }
} 
 
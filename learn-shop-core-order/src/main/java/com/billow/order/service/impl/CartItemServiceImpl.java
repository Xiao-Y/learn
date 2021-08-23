package com.billow.order.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.order.dao.CartItemDao;
import com.billow.order.pojo.po.CartItemPo;
import com.billow.order.pojo.search.CartItemSearchParam;
import com.billow.order.service.CartItemService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-23
 */
@Service
public class CartItemServiceImpl extends HighLevelServiceImpl<CartItemDao, CartItemPo,CartItemSearchParam> implements CartItemService {

}


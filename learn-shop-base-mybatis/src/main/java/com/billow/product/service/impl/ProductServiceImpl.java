package com.billow.product.service.impl;

import com.billow.product.pojo.po.ProductPo;
import com.billow.product.mapper.ProductMapper;
import com.billow.product.service.ProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品 服务实现类
 * </p>
 *
 * @author billow
 * @since 2019-10-31
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, ProductPo> implements ProductService {

}

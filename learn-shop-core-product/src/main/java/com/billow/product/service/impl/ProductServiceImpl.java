package com.billow.product.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.billow.product.dao.AttributeKeyDao;
import com.billow.product.dao.AttributsValueDao;
import com.billow.product.dao.ProductDao;
import com.billow.product.dao.ProductSpcesDao;
import com.billow.product.pojo.po.AttributeKeyPo;
import com.billow.product.pojo.po.AttributsValuePo;
import com.billow.product.pojo.po.ProductPo;
import com.billow.product.pojo.po.ProductSpcesPo;
import com.billow.product.pojo.vo.ProductSkuVo;
import com.billow.product.pojo.vo.ProductSpcesVo;
import com.billow.product.pojo.vo.ProductVo;
import com.billow.product.service.ProductService;
import com.billow.product.service.ProductSpcesService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.tools.utlis.ToolsUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 商品信息 服务实现类
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductDao, ProductPo> implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductSpcesDao productSpcesDao;
    @Autowired
    private AttributeKeyDao attributeKeyDao;
    @Autowired
    private AttributsValueDao attributsValueDao;

    @Override
    public IPage<ProductPo> findListByPage(ProductVo productVo) {
        IPage<ProductPo> page = new Page<>(productVo.getPageNo(), productVo.getPageSize());
        LambdaQueryWrapper<ProductPo> wrapper = Wrappers.lambdaQuery();
        // 查询条件
        IPage<ProductPo> selectPage = productDao.selectPage(page, wrapper);
        return selectPage;
    }

    @Override
    public boolean prohibitById(String id) {
        ProductPo po = new ProductPo();
        po.setValidInd(false);
        LambdaQueryWrapper<ProductPo> wrapper = Wrappers.lambdaQuery();
        wrapper.eq(ProductPo::getId, id);
        return productDao.update(po, wrapper) >= 1;
    }

    @Override
    public ProductSkuVo findProductSku(String id) {
        ProductSkuVo productSkuVo = new ProductSkuVo();
        ProductPo productPo = productDao.selectById(id);
        ConvertUtils.convert(productSkuVo, productPo);
        productSkuVo.setHide_stock(productPo.getHideStock());
        productSkuVo.setStock_num(productPo.getStockNum());

        // 商品属性
        Map<String, List<AttributsValuePo>> productArr = new HashMap<>();

        List<ProductSpcesVo> list = new ArrayList<>();
        LambdaQueryWrapper<ProductSpcesPo> psp = Wrappers.lambdaQuery();
        psp.eq(ProductSpcesPo::getProductId, id);
        List<ProductSpcesPo> productSpcesPos = productSpcesDao.selectList(psp);
        productSpcesPos.stream().forEach(f -> {
            ProductSpcesVo productSpcesVo = new ProductSpcesVo();
            ConvertUtils.convert(f, productSpcesVo);
            productSpcesVo.setStock_num(f.getStockNum());
            list.add(productSpcesVo);

            String productSpces = f.getProductSpces();
            if (ToolsUtils.isNotEmpty(productSpces)) {
                // 属性keyid和属性valueid
                List<Map<String, String>> spces = JSONObject.parseObject(productSpces, List.class);
                spces.stream().forEach(sp -> {
                    sp.forEach((k, v) -> {
                        if (productArr.containsKey(k)) {

                        } else {
                            // 查询出属性值
                            LambdaQueryWrapper<AttributsValuePo> attp = Wrappers.lambdaQuery();
                            attp.eq(AttributsValuePo::getAttributeKeyId, k);
                            List<AttributsValuePo> attributsValuePos = attributsValueDao.selectList(attp);
                            productArr.put(k, attributsValuePos);
                        }
                    });

                });
                // 构建 tree

            }

        });
        productSkuVo.setList(list);


        return null;
    }
}


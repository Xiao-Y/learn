package com.billow.product.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 商品sku
 *
 * @author liuyongtao
 * @create 2019-11-26 11:15
 */
@Data
public class ProductSkuVo {

    private List<ProductSkuTree> tree;

    private List<ProductSpcesVo> list;

    private String collection_id;

    private Integer stock_num;

    private boolean hide_stock; // 是否隐藏剩余库存
}

package com.billow.product.pojo.vo;

import lombok.Data;

import java.util.List;

/**
 * 一个商品可以有多个规格类目，一个规格类目下可以有多个规格值。
 *
 * @author liuyongtao
 * @create 2019-11-26 11:24
 */
@Data
public class ProductSkuTree {

    // skuKeyName：规格类目名称
    private String k;
    private List<ProductSukValue> v;
    // skuKeyStr：sku 组合列表（下方 list）中当前类目对应的 key 值，value 值会是从属于当前类目的一个规格值 id
    private String k_s;
}

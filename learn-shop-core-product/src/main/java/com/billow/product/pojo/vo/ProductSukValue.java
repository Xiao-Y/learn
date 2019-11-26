package com.billow.product.pojo.vo;

import lombok.Data;

/**
 * @author liuyongtao
 * @create 2019-11-26 11:27
 */
@Data
public class ProductSukValue {

    // skuValueId：规格值 id
    private String id;
    // skuValueName：规格值名称
    private String name;
    // 规格类目图片，只有第一个规格类目可以定义图片
    private String imgUrl;
    // 用于预览显示的规格类目图片
    private String previewImgUrl;
}

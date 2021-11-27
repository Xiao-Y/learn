package com.billow.product.service;

/**
 * 导入商品列表数据
 *
 * @author 千面
 * @date 2021/11/23 11:08
 */
public interface ImportJdGoodsDetailService
{
    /**
     * 保存sku、规格、规格value 相关的数据
     *
     * @param spu
     * @return void
     * @author 千面
     * @date 2021/11/23 11:19
     */
    void importGoodsSku(String spu);
}

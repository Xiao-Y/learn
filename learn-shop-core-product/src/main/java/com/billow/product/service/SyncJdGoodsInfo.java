package com.billow.product.service;

public interface SyncJdGoodsInfo {
    /**
     * 通过关键字查询商品数据
     *
     * @param keyword
     * @return boolean
     * @author 千面
     * @date 2021/11/23 11:15
     */
    boolean syncJdGoods(String keyword) throws Exception;
}

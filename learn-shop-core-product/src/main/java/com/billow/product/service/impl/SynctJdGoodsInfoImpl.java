package com.billow.product.service.impl;

import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.pojo.vo.SyncJdGoods;
import com.billow.product.service.SyncJdGoodsDetailService;
import com.billow.product.service.SyncJdGoodsInfo;
import com.billow.product.service.SyncJdGoodsListService;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SynctJdGoodsInfoImpl implements SyncJdGoodsInfo {
    @Autowired
    private SyncJdGoodsDetailService syncJdGoodsDetailService;
    @Autowired
    private SyncJdGoodsListService syncJdGoodsListService;

    @Override
    public boolean syncJdGoods(String keyword) throws Exception {
        // 获取商品列表页面
        Element GoodsListBody = syncJdGoodsListService.startRequest(keyword, 1);
        // 解析出品牌信息
        List<GoodsBrandPo> brandPos = syncJdGoodsListService.parseBrand(GoodsListBody);
        // 解析出店铺信息
        List<ShopInfoPo> shopInfoPos = syncJdGoodsListService.parseShopInfo(GoodsListBody);
        // 解析出商品信息
        List<GoodsSpuPo> goodsSpuPos = syncJdGoodsListService.parseGoodsList(GoodsListBody);
        for (GoodsSpuPo spuPo : goodsSpuPos) {
            // 获取商品详细页面
            Element goodsSpuBody = syncJdGoodsDetailService.startRequest(spuPo.getSpuNo());
            // 解析出商品详细信息(规格信息)
            SyncJdGoods syncJdGoods = syncJdGoodsDetailService.parseGoodsSku(goodsSpuBody);
            List<GoodsSkuVo> goodsSkuVos = syncJdGoods.getGoodsSkuVos();
            List<GoodsSpecKeyPo> specKeyPos = syncJdGoods.getSpecKeyPos();
            List<GoodsSpecValuePo> specValuePos = syncJdGoods.getSpecValuePos();
        }
        return false;
    }
}

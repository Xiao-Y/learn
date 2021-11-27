package com.billow.product.service.impl;

import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.pojo.vo.ImportJdGoods;
import com.billow.product.service.ImportJdGoodsDetailService;
import com.billow.product.service.ImportJdGoodsInfo;
import com.billow.product.service.ImportJdGoodsListService;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImportJdGoodsInfoImpl implements ImportJdGoodsInfo {
    @Autowired
    private ImportJdGoodsDetailService importJdGoodsDetailService;
    @Autowired
    private ImportJdGoodsListService importJdGoodsListService;

    @Override
    public boolean importJdGoods(String keyword) throws Exception {
        // 获取商品列表页面
        Element GoodsListBody = importJdGoodsListService.startRequest(keyword, 1);
        // 解析出品牌信息
        List<GoodsBrandPo> brandPos = importJdGoodsListService.parseBrand(GoodsListBody);
        // 解析出店铺信息
        List<ShopInfoPo> shopInfoPos = importJdGoodsListService.parseShopInfo(GoodsListBody);
        // 解析出商品信息
        List<GoodsSpuPo> goodsSpuPos = importJdGoodsListService.parseGoodsList(GoodsListBody);
        for (GoodsSpuPo spuPo : goodsSpuPos) {
            // 获取商品详细页面
            Element goodsSpuBody = importJdGoodsDetailService.startRequest(spuPo.getSpuNo());
            // 解析出商品详细信息(规格信息)
            ImportJdGoods importJdGoods = importJdGoodsDetailService.parseGoodsSku(goodsSpuBody);
            List<GoodsSkuVo> goodsSkuVos = importJdGoods.getGoodsSkuVos();
            List<GoodsSpecKeyPo> specKeyPos = importJdGoods.getSpecKeyPos();
            List<GoodsSpecValuePo> specValuePos = importJdGoods.getSpecValuePos();
        }

        return false;
    }
}

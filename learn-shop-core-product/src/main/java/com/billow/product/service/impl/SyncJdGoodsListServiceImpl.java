package com.billow.product.service.impl;

import com.billow.product.common.enums.SpuPublishStatusEnum;
import com.billow.product.common.enums.SpuVerifyStatusEnum;
import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.po.GoodsSpuPo;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.service.SyncJdGoodsListService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
public class SyncJdGoodsListServiceImpl implements SyncJdGoodsListService {

    @Override
    public Element startRequest(String keyword, int page) throws Exception {
        String url = "https://search.jd.com/Search?keyword=" + URLEncoder.encode(keyword, "utf-8") + "&&enc=utf-8&page=" + page;
        Document document = Jsoup.parse(new URL(url), 3000);
        Element element = document.body();
        return element;
    }

    @Override
    public List<GoodsSpuPo> parseGoodsList(Element body) {
        // 商品信息
        List<GoodsSpuPo> goodsSpuPos = new ArrayList<>();
        // 获取列表商品
        Element goodsList = body.getElementById("J_goodsList");
        // 获取单个商品信息
        Elements items = goodsList.getElementsByClass("gl-item");
        for (Element item : items) {
            // 获取 imag-div 信息
            Element imgElement = item.getElementsByClass("p-img").get(0);
            // 获取 imag-div-a 信息
            Elements aTag = imgElement.getElementsByTag("a");
            // 获取 imag-div-a 中 title 属性
            String title = aTag.get(0).attr("title");
            // 获取 imag-div-a 中 img 信息，可以打印 html 查看 真实图片地址（图片懒加载）
            String img = aTag.get(0).getElementsByTag("img").get(0).attr("data-lazy-img");
            // 获取 价格div 信息
            String price = item.getElementsByClass("p-price").get(0)
                    .getElementsByTag("i").text();

            // 获取 商品 div 信息
            Element goodsNameElement = item.getElementsByClass("p-name").get(0);
            // 商品信息
            String spuNo = goodsNameElement.getElementsByTag("a").attr("onclick");
            String spuName = goodsNameElement.text();

            // 商品信息
            GoodsSpuPo spuPo = new GoodsSpuPo();
            spuPo.setSpuNo(parseNum(spuNo));
//            spuPo.setProductAttributeCategoryId();
//            spuPo.setCategoryId()
//            spuPo.setBrandId()
            spuPo.setGoodsName(title);
            spuPo.setSubTitle(title);
            spuPo.setDetailTitle(spuName);
            spuPo.setPic(img);
            spuPo.setPublishStatus(SpuPublishStatusEnum.UP.getStatus());
//            spuPo.setNewStatus()
//            spuPo.setRecommandStatus()
//            spuPo.setPreviewStatus()
//            spuPo.setServiceIds()
            spuPo.setVerifyStatus(SpuVerifyStatusEnum.APPROVED.getStatus());
            spuPo.setPrice(Integer.valueOf(price) * 100);
            spuPo.setLowPrice(spuPo.getPrice() - 10);
            spuPo.setSale(Long.valueOf(spuPo.getPrice() * 3));
            spuPo.setStock(Long.valueOf(spuPo.getPrice() * 7));
            spuPo.setLowStock(spuPo.getStock() - 11);
            spuPo.setAlbumPics(spuPo.getPic());
//            spuPo.setDescription()
//            spuPo.setDetailHtml()
//            spuPo.setDetailMobileHtml()
//            spuPo.setFeightTemplateId()
            goodsSpuPos.add(spuPo);
        }
        return goodsSpuPos;
    }

    @Override
    public List<GoodsBrandPo> parseBrand(Element body) {
        List<GoodsBrandPo> brandPos = new ArrayList<>();
        // 获取logo 元素
        Element logosEl = body.getElementsByClass("sl-v-logos").get(0);
        Elements brandLi = logosEl.getElementsByTag("li");
        for (Element el : brandLi) {
            String brandId = el.attr("id").split("-")[1];
            String firstLetter = el.attr("data-initial");
            String brandName = el.getElementsByTag("a").eq(0).attr("title");
            String logo = el.getElementsByTag("img").eq(0).attr("src");

            GoodsBrandPo brandPo = new GoodsBrandPo();
            brandPo.setId(Long.valueOf(brandId));
            brandPo.setFirstLetter(firstLetter);
            brandPo.setBrandName(brandName);
            brandPo.setBrandSort(brandPo.getId());
            brandPo.setShowStatus(1);
            brandPo.setProductCount(brandPo.getId().intValue());
            brandPo.setLogo(logo);
            brandPo.setBigPic(logo);
            brandPo.setBrandStory(brandName);
            brandPos.add(brandPo);
        }
        return brandPos;
    }

    @Override
    public List<ShopInfoPo> parseShopInfo(Element body) {
        // 商铺信息
        Map<String, ShopInfoPo> map = new HashMap<>();
        // 获取单个商品信息
        Elements items = body.getElementsByClass("gl-item");
        for (Element item : items) {
            // 获取 店铺 div 信息
            Element shopnumElement = item.getElementsByClass("p-shopnum").get(0);
            // 店铺信息
            String shopNum = shopnumElement.getElementsByTag("a").attr("onclick");
            String shopName = shopnumElement.text();

            String parseNum = parseNum(shopNum);
            // 商铺信息
            ShopInfoPo shopInfoPo = new ShopInfoPo();
            shopInfoPo.setShopName(shopName);
            shopInfoPo.setShopNo(parseNum);
            map.put(parseNum, shopInfoPo);
        }
        return new ArrayList<>(map.values());
    }

    private static String parseNum(String str) {
//        log.info("原始：{}", str);
        Matcher m = Pattern.compile("\'(.*?)\'").matcher(str);
        if (m.find()) {
            String s = m.group().trim().replace("\'", "");
            log.info("替换：{}", s);
            return s;
        }
        return "000";
    }
}

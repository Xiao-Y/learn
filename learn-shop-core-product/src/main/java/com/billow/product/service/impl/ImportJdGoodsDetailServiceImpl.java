package com.billow.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import com.billow.product.pojo.vo.GoodsSkuSpecValueVo;
import com.billow.product.pojo.vo.GoodsSkuVo;
import com.billow.product.pojo.vo.ImportJdGoods;
import com.billow.product.service.ImportJdGoodsDetailService;
import com.billow.tools.generator.NumUtil;
import com.billow.tools.utlis.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ImportJdGoodsDetailServiceImpl implements ImportJdGoodsDetailService {

    private static final String SKU_ID = "skuId";

    @Override
    public Element startRequest(String skuNo) throws Exception {
        String url = "https://item.jd.com/" + skuNo + ".html";
        Document parse = Jsoup.connect(url).get();
        Element element = parse.body();
        return element;
    }

    @Override
    public ImportJdGoods parseGoodsSku(Element element) throws Exception {
        // 获取 sku 的 script
        String skuScript = element.select("script").first().data();
        // 解析规定属性，构建 specKey 和 specValue,同时返回所有的skuNo和记录规格/规格值的 id
        ImportJdGoods importJdGoods = this.genSpecKeyAndValue(skuScript);
        // 记录规格/规格值的 id
        Map<String, String> specMap = importJdGoods.getSpecMap();
        // 所有的 skuNo
        List<String> skus = importJdGoods.getSkus();
        List<GoodsSkuVo> goodsSkuVos = skus.stream().map(skuNo -> {
            try {
                return parseGoodsBySku(skuNo, importJdGoods);
            } catch (Exception e) {
                log.error("skuNo:{} 异常", skuNo);
            }
            return null;
        }).collect(Collectors.toList());
        importJdGoods.setGoodsSkuVos(goodsSkuVos);
        return importJdGoods;
    }

    /**
     * 通过sku 解析 商品详细.组装 sku 和商品规则信息
     *
     * @param skuNo
     * @param importJdGoods com.billow.product.service.impl.ImportJdGoodsDetailServiceImpl#genSpecKeyAndValue(java.lang.String) 方法解析后的sku数据
     * @return {@link Object}
     * @author xiaoy
     * @since 2021/11/22 21:21
     */
    private GoodsSkuVo parseGoodsBySku(String skuNo, ImportJdGoods importJdGoods) throws Exception {

        AtomicLong sort = new AtomicLong(1);
        // 构建 sku 对象
        GoodsSkuVo skuVo = new GoodsSkuVo();
        skuVo.setId(SnowFlakeUtil.getFlowIdInstance().nextId());
        skuVo.setSkuNo(skuNo);
        // sku 规格信息
        List<GoodsSkuSpecValueVo> goodsSkuSpecValueVos = new ArrayList<>();
        String skuName = "";
        // 记录规格/规格值的 id
        Map<String, String> specMap = importJdGoods.getSpecMap();
        // 获取 sku 页面
        Element body = this.startRequest(skuNo);
        // 可选择的类型
        Elements chooseEl = body.getElementById("choose-attrs").getElementsByClass("p-choose");
        for (Element el : chooseEl) {
            String specName = el.attr("data-type");
            String specKeyId = specMap.get(specName);
            // 获取选种的 sku 信息
            Element selected = el.getElementsByClass("selected").get(0);
            String specValue = selected.attr("data-value");
            String specValueId = specMap.get(specValue);
            skuName = String.join("/", skuName);
            // sku 小图片
            String skuImg = selected.getElementsByTag("img").get(0).attr("src");
            GoodsSkuSpecValueVo skuSpecValueVo = new GoodsSkuSpecValueVo();
//            skuSpecValueVo.setSpuId();
            skuSpecValueVo.setSkuId(skuVo.getId());
            skuSpecValueVo.setSpecKeyId(Long.valueOf(specKeyId));
            skuSpecValueVo.setSpecValueId(Long.valueOf(specValueId));
            skuSpecValueVo.setSkuSpecSort(sort.getAndIncrement());
            goodsSkuSpecValueVos.add(skuSpecValueVo);
        }
        // 京 东 价
        String price = body.getElementsByClass("p-price").get(0).getElementsByClass("price").get(0).text();
        skuVo.setSkuName(skuName);
        skuVo.setPrice(Integer.valueOf(price) * 100);
        skuVo.setStock(skuVo.getPrice() * 10);
        skuVo.setStock(skuVo.getPrice() * 2);
        // 左侧图片
        List<String> imgs = parseLeftImg(body);
        skuVo.setPic(JSON.toJSONString(imgs));
        skuVo.setSale(skuVo.getPrice() * 223);
//        skuVo.setShopId()
//        skuVo.setSpuId()
        skuVo.setGoodsSkuSpecValueVos(goodsSkuSpecValueVos);
        return skuVo;
    }

    /**
     * 解析规定属性，构建 specKey 和 specValue,同时返回所有的skuNo和记录规格/规格值的 id
     *
     * @param skuScript
     * @return ImportJdGoods
     * @author 千面
     * @date 2021/11/23 16:33
     */
    private ImportJdGoods genSpecKeyAndValue(String skuScript) {
        // 所有的 skuNo
        List<String> skus = new ArrayList<>();
        // 记录规格/规格值的 id
        Map<String, String> specMap = new HashMap<>();

        // 构建 specKey
        List<GoodsSpecKeyPo> specKeyVos = new ArrayList<>();
        // 构建 specValue
        List<GoodsSpecValuePo> specValueVos = new ArrayList<>();

        AtomicLong sortKey = new AtomicLong(1);
        AtomicLong sortValue = new AtomicLong(1);
        // 解析后的规格和值
        List<Map<String, String>> list = this.parseSkuScript(skuScript);
        list.forEach(f -> {
            // 每一个规格值
            f.forEach((key, value) -> {
                if (Objects.equals(key, SKU_ID)) {
                    skus.add(value);
                }
                // 记录 规格 key
                if (!specMap.containsKey(key)) {
                    GoodsSpecKeyPo specKeyPo = new GoodsSpecKeyPo();
                    specKeyPo.setId(SnowFlakeUtil.getFlowIdInstance().nextId());
                    specKeyPo.setSpecNo(NumUtil.makeNum("SP"));
                    specKeyPo.setSpecName(key);
                    specKeyPo.setKeySort(sortKey.getAndIncrement());
                    specKeyVos.add(specKeyPo);
                    specMap.put(key, specKeyPo.getId().toString());
                }
                // 记录 规格 value
                if (!specMap.containsKey(value)) {
                    GoodsSpecValuePo valuePo = new GoodsSpecValuePo();
                    valuePo.setId(SnowFlakeUtil.getFlowIdInstance().nextId());
                    // 获取规格的 id
                    valuePo.setSpecKeyId(Long.valueOf(specMap.get(key)));
                    valuePo.setSpecValue(value);
                    valuePo.setValueSort(sortValue.getAndIncrement());
                    specValueVos.add(valuePo);
                    specMap.put(value, valuePo.getId().toString());
                }

            });
        });
        return new ImportJdGoods()
                .setSpecValuePos(specValueVos)
                .setSpecKeyPos(specKeyVos)
                .setSpecMap(specMap)
                .setSkus(skus);
    }

    /**
     * 解析 Script 中所有的 sku 规格值
     *
     * @param skuScript
     * @return List<Map < String, String>>
     * @author 千面
     * @date 2021/11/23 15:23
     */
    public List<Map<String, String>> parseSkuScript(String skuScript) {
        String temp = skuScript.replace("\n", "").replace(" ", "");
        String startStr = "colorSize:";
        int start = temp.indexOf(startStr);
        temp = temp.substring(start);
        int end = temp.indexOf("}],");
        temp = temp.substring(startStr.length(), end + 2);
        return JSON.parseObject(temp, new TypeReference<List<Map<String, String>>>() {
        });
    }

    /**
     * 获取左侧展示图片
     *
     * @param body
     * @return {@link List< String>}
     * @author xiaoy
     * @since 2021/11/22 21:49
     */
    private List<String> parseLeftImg(Element body) {
        // //img13.360buyimg.com/n5/jfs/t1/186038/9/7947/120952/60bdd993E41eea7e2/48ab930455d7381b.jpg
        // 其中的 n5 换成 n1 就是大图
        List<String> imgs = new ArrayList<>();
        Elements imgEls = body.getElementById("spec-list").getElementsByTag("img");
        for (Element el : imgEls) {
            imgs.add(el.attr("src"));
        }
        return imgs;
    }

}

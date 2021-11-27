package com.billow.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.billow.product.pojo.vo.*;
import com.billow.product.service.ImportJdGoodsDetailService;
import com.billow.tools.generator.NumUtil;
import com.billow.tools.utlis.SnowFlakeUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Service
public class ImportJdGoodsDetailServiceImpl implements ImportJdGoodsDetailService
{

    private static final String SKU_ID = "skuId";

    @Override
    public void importGoodsSku(String spu)
    {
        AtomicLong sort = new AtomicLong(1);
        String skuScript = "";
        // 解析规定属性，构建 specKey 和 specValue,同时返回所有的skuNo和记录规格/规格值的 id
        ImportJdGoods importJdGoods = this.genSpecKeyAndValue(skuScript);
        // 记录规格/规格值的 id
        Map<String, String> specMap = importJdGoods.getSpecMap();
        // 所有的 skuNo
        List<String> skus = importJdGoods.getSkus();
        for (String skuNo : skus)
        {
            GoodsSkuVo skuVo = new GoodsSkuVo();
            skuVo.setId(SnowFlakeUtil.getFlowIdInstance().nextId());
            skuVo.setSkuNo(skuNo);


            GoodsSkuSpecValueVo skuSpecValueVo = new GoodsSkuSpecValueVo();
//            skuSpecValueVo.setSpuId();
            skuSpecValueVo.setSkuId(skuVo.getId());
//            skuSpecValueVo.setSpecKeyId();
//            skuSpecValueVo.setSpecValueId();
            skuSpecValueVo.setSkuSpecSort(sort.getAndIncrement());
        }

    }

    /**
     * 解析规定属性，构建 specKey 和 specValue,同时返回所有的skuNo和记录规格/规格值的 id
     *
     * @param skuScript
     * @return ImportJdGoods
     * @author 千面
     * @date 2021/11/23 16:33
     */
    private ImportJdGoods genSpecKeyAndValue(String skuScript)
    {
        // 所有的 skuNo
        List<String> skus = new ArrayList<>();
        // 记录规格/规格值的 id
        Map<String, String> specMap = new HashMap<>();

        // 构建 specKey
        List<GoodsSpecKeyVo> specKeyVos = new ArrayList<>();
        // 构建 specValue
        List<GoodsSpecValueVo> specValueVos = new ArrayList<>();

        AtomicLong sortKey = new AtomicLong(1);
        AtomicLong sortValue = new AtomicLong(1);
        // 解析后的规格和值
        List<Map<String, String>> list = this.parseSku(skuScript);
        list.forEach(f -> {
            // 每一个规格值
            f.forEach((key, value) -> {
                if (Objects.equals(key, SKU_ID))
                {
                    skus.add(value);
                }
                // 记录 规格 key
                if (!specMap.containsKey(key))
                {
                    GoodsSpecKeyVo specKeyVo = new GoodsSpecKeyVo();
                    specKeyVo.setId(SnowFlakeUtil.getFlowIdInstance().nextId());
                    specKeyVo.setSpecNo(NumUtil.makeNum("SP"));
                    specKeyVo.setSpecName(key);
                    specKeyVo.setKeySort(sortKey.getAndIncrement());
                    specKeyVos.add(specKeyVo);
                    specMap.put(key, specKeyVo.getId().toString());
                }
                // 记录 规格 value
                if (!specMap.containsKey(value))
                {
                    GoodsSpecValueVo valueVo = new GoodsSpecValueVo();
                    valueVo.setId(SnowFlakeUtil.getFlowIdInstance().nextId());
                    // 获取规格的 id
                    valueVo.setSpecKeyId(Long.valueOf(specMap.get(key)));
                    valueVo.setSpecValue(value);
                    valueVo.setValueSort(sortValue.getAndIncrement());
                    specValueVos.add(valueVo);
                    specMap.put(value, valueVo.getId().toString());
                }

            });
        });
        return new ImportJdGoods()
                .setSpecValueVos(specValueVos)
                .setSpecKeyVos(specKeyVos)
                .setSpecMap(specMap)
                .setSkus(skus);
    }

    /**
     * 解析出所有的 sku规格值
     *
     * @param skuScript
     * @return List<Map < String, String>>
     * @author 千面
     * @date 2021/11/23 15:23
     */
    public List<Map<String, String>> parseSku(String skuScript)
    {
        String temp = skuScript.replace("\n", "").replace(" ", "");
        String startStr = "colorSize:";
        int start = temp.indexOf(startStr);
        temp = temp.substring(start);
        int end = temp.indexOf("}],");
        temp = temp.substring(startStr.length(), end + 2);
        return JSON.parseObject(temp, new TypeReference<List<Map<String, String>>>()
        {
        });
    }
}

package com.billow.product.pojo.vo;

import com.billow.product.pojo.po.GoodsSpecKeyPo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Accessors(chain = true)
@Data
public class ImportJdGoods {
    // 所有的 skuNo
    List<String> skus = new ArrayList<>();

    // 记录规格/规格值的 id
    Map<String, String> specMap = new HashMap<>();
    // 构建 specKey
    List<GoodsSpecKeyPo> specKeyPos = new ArrayList<>();
    // 构建 specValue
    List<GoodsSpecValuePo> specValuePos = new ArrayList<>();
    // 构建 sku 信息
    List<GoodsSkuVo> goodsSkuVos = new ArrayList<>();

}

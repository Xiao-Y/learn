package com.billow.product.pojo.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Accessors(chain = true)
@Data
public class ImportJdGoods
{
    // 所有的 skuNo
    List<String> skus = new ArrayList<>();

    // 记录规格/规格值的 id
    Map<String, String> specMap = new HashMap<>();
    // 构建 specKey
    List<GoodsSpecKeyVo> specKeyVos = new ArrayList<>();
    // 构建 specValue
    List<GoodsSpecValueVo> specValueVos = new ArrayList<>();
}

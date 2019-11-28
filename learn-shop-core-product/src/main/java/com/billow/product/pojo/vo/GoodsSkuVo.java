package com.billow.product.pojo.vo;


import com.alibaba.fastjson.JSONObject;
import com.billow.product.pojo.po.GoodsSkuPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * <p>
 * sku表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSkuVo extends GoodsSkuPo implements Serializable {

    // 规格key 和 规格值
    private Map<String, String> specKeyValue = new LinkedHashMap<>();

    public String getSpecKeyValue() {
        return JSONObject.toJSONString(specKeyValue);
    }
}

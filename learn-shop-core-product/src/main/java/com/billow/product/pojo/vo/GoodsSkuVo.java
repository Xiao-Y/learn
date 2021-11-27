package com.billow.product.pojo.vo;


import com.alibaba.fastjson.JSONObject;
import com.billow.product.pojo.po.GoodsSkuPo;
import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import com.billow.product.pojo.po.GoodsSpecValuePo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
public class GoodsSkuVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    @ApiModelProperty(value = "sku编号,唯一")
    private String skuNo;

    @ApiModelProperty(value = "sku名称(冗余spu_name)")
    private String skuName;

    @ApiModelProperty(value = "售价")
    private Integer price;

    @ApiModelProperty(value = "库存")
    private Integer stock;

    @ApiModelProperty(value = "锁定库存")
    private Integer lockStock;

    @ApiModelProperty(value = "预警库存")
    private Integer lowStock;

    @ApiModelProperty(value = "展示图片")
    private String pic;

    @ApiModelProperty(value = "销量")
    private Integer sale;

    @ApiModelProperty(value = "商铺id,为0表示自营")
    private Long shopId;

    @ApiModelProperty(value = "spu_id")
    private Long spuId;

    // 规格key 和 规格值，页面显示
    private String specKeyValueName;
    // 规格key 和 规格值
    private List<GoodsSkuSpecValueVo> goodsSkuSpecValueVos = new ArrayList<>();

}

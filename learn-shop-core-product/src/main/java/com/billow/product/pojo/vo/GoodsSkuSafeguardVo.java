package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSkuSafeguardPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * sku增值保障 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSkuSafeguardVo extends GoodsSkuSafeguardPo implements Serializable {

}

package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSkuSpecValuePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * sku规格值 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSkuSpecValueVo extends GoodsSkuSpecValuePo implements Serializable {

}

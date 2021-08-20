package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSpuDetailPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 *  信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSpuDetailVo extends GoodsSpuDetailPo implements Serializable {

}

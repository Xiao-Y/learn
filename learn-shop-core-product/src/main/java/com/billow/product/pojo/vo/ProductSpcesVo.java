package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.ProductSpcesPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品规格 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class ProductSpcesVo extends ProductSpcesPo implements Serializable {

    private Integer stock_num;
}

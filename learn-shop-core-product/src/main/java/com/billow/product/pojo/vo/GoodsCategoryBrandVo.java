package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsCategoryBrandPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品分类和品牌的中间表，两者是多对多关系 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-02-06
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsCategoryBrandVo extends GoodsCategoryBrandPo implements Serializable {

}

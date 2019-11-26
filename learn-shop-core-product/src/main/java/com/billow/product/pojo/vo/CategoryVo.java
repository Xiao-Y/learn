package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.CategoryPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品分类 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-26
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryVo extends CategoryPo implements Serializable {

}

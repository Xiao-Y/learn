package com.billow.product.interfaces.vo;

import lombok.Data;
import lombok.experimental.Accessors;

/**
 * <p>
 * 品牌表
 * </p>
 *
 * @author billow
 * @since 2019-11-29
 */
@Data
@Accessors(chain = true)
public class GoodsBrandVo {

    private static final long serialVersionUID = 1L;

    /**
     * 品牌名称
     *
     * @author xiaoy
     * @since 2021/2/4 16:27
     */
    private String brandName;

    /**
     * 分类排序
     *
     * @author xiaoy
     * @since 2021/2/4 16:27
     */
    private Long brandSort;


}

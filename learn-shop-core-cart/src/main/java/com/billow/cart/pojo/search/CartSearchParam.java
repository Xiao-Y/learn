package com.billow.cart.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * 购物车查询参数
 *
 * @author liuyongtao
 * @since 2024-01-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CartSearchParam extends BasePage implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    private Long userId;

    @ApiModelProperty(value = "SKU ID")
    private Long skuId;

    @ApiModelProperty(value = "SKU ID列表")
    private List<Long> skuIds;

    @ApiModelProperty(value = "是否选中")
    private Boolean selected;
} 
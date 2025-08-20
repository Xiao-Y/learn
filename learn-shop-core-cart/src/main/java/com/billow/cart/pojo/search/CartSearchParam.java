package com.billow.cart.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(title = "用户ID")
    private Long userId;

    @Schema(title = "SKU ID")
    private Long skuId;

    @Schema(title = "SKU ID列表")
    private List<Long> skuIds;

    @Schema(title = "是否选中")
    private Boolean selected;
} 
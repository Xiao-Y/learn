package com.billow.product.pojo.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 增值保障 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
public class GoodsSafeguardVo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Schema(title = "保障名称")
    private String safeguardName;

    @Schema(title = "保障价格")
    private Integer price;


}

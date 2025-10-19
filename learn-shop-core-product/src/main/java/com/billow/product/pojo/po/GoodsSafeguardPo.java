package com.billow.product.pojo.po;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Table;
import com.billow.mybatis.pojo.BasePo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 增值保障
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("pms_goods_safeguard")
@Schema(title = "GoodsSafeguardPo对象", description="增值保障")
public class GoodsSafeguardPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "保障名称")
    @Column("safeguard_name")
    private String safeguardName;

    @Schema(title = "保障价格")
    @Column("price")
    private Integer price;


}

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
 * 规格表
 * </p>
 *
 * @author billow
 * @since 2021-09-02
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Table("pms_goods_spec_key")
@Schema(title = "GoodsSpecKeyPo对象", description="规格表")
public class GoodsSpecKeyPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "规格编号")
    @Column("spec_no")
    private String specNo;

    @Schema(title = "规格名称")
    @Column("spec_name")
    private String specName;

    @Schema(title = "规格排序")
    @Column("key_sort")
    private Long keySort;

    @Schema(title = "分类id")
    @Column("category_id")
    private Long categoryId;


}

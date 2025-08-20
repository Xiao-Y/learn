package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
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
@TableName("pms_goods_spec_key")
@Schema(title = "GoodsSpecKeyPo对象", description="规格表")
public class GoodsSpecKeyPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @Schema(title = "规格编号")
    @TableField("spec_no")
    private String specNo;

    @Schema(title = "规格名称")
    @TableField("spec_name")
    private String specName;

    @Schema(title = "规格排序")
    @TableField("key_sort")
    private Long keySort;

    @Schema(title = "分类id")
    @TableField("category_id")
    private Long categoryId;


}

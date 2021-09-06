package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@TableName("pms_goods_safeguard")
@ApiModel(value="GoodsSafeguardPo对象", description="增值保障")
public class GoodsSafeguardPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "保障名称")
    @TableField("safeguard_name")
    private String safeguardName;

    @ApiModelProperty(value = "保障价格")
    @TableField("price")
    private Integer price;


}

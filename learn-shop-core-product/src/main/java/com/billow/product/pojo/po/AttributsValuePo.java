package com.billow.product.pojo.po;

import com.baomidou.mybatisplus.annotation.TableName;
import com.billow.mybatis.pojo.BasePo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 商品属性值
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_attributs_value")
@ApiModel(value="AttributsValuePo对象", description="商品属性值")
public class AttributsValuePo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "p_attribute_key的主键")
    private String attributeKeyId;

    @ApiModelProperty(value = "属性值")
    private String attributeValue;

    @ApiModelProperty(value = "属性值排序")
    private Integer valueSort;


}

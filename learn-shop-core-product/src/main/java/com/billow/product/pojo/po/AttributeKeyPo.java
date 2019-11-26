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
 * 商品属性key
 * </p>
 *
 * @author billow
 * @since 2019-11-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("p_attribute_key")
@ApiModel(value="AttributeKeyPo对象", description="商品属性key")
public class AttributeKeyPo extends BasePo {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "分类主键")
    private Integer categoryId;

    @ApiModelProperty(value = "属性名")
    private String attributeName;

    @ApiModelProperty(value = "属性排序")
    private Integer nameSort;


}

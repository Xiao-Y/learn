package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSpecValuePo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 规格值表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSpecValueVo extends GoodsSpecValuePo implements Serializable {
    // 页面只读
    private boolean readonly = true;
}

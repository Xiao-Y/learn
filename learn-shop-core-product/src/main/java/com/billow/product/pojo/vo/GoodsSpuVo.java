package com.billow.product.pojo.vo;


import com.billow.product.pojo.po.GoodsSpuPo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * spu表 信息
 * </p>
 *
 * @author billow
 * @version v1.0
 * @since 2019-11-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsSpuVo extends GoodsSpuPo implements Serializable {
    private List<String> specKeys;
}

package com.billow.product.pojo.search;

import com.billow.mybatis.pojo.BasePage;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 商品操作记录表，用于记录商品操作记录

 信息
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class GoodsOperateLogSearchParam extends BasePage implements Serializable {

}

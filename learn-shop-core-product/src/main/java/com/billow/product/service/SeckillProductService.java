
package com.billow.product.service;

import com.billow.mybatis.base.HighLevelService;
import com.billow.product.pojo.po.SeckillProductPo;
import com.billow.product.pojo.search.SeckillProductSearchParam;

/**
 * <p>
 * 限时购与商品关系表。用于存储与限时购相关的商品信息，一个限时购中有多个场次，每个场次都可以设置不同活动商品。 服务类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-08-31
 */
public interface SeckillProductService extends HighLevelService<SeckillProductPo,SeckillProductSearchParam> {

    void loadData();

}
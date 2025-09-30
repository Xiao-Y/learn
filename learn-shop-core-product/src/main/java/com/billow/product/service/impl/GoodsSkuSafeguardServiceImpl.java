package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsSkuSafeguardDao;
import com.billow.product.pojo.po.GoodsSkuSafeguardPo;
import com.billow.product.pojo.search.GoodsSkuSafeguardSearchParam;
import com.billow.product.service.GoodsSkuSafeguardService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * sku增值保障 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class GoodsSkuSafeguardServiceImpl extends HighLevelServiceImpl<GoodsSkuSafeguardDao, GoodsSkuSafeguardPo,GoodsSkuSafeguardSearchParam> implements GoodsSkuSafeguardService {

}


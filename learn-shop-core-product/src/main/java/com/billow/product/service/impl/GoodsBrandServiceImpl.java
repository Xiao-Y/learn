package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsBrandDao;
import com.billow.product.pojo.po.GoodsBrandPo;
import com.billow.product.pojo.search.GoodsBrandSearchParam;
import com.billow.product.service.GoodsBrandService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 品牌表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class GoodsBrandServiceImpl extends HighLevelServiceImpl<GoodsBrandDao, GoodsBrandPo, GoodsBrandSearchParam> implements GoodsBrandService {

}


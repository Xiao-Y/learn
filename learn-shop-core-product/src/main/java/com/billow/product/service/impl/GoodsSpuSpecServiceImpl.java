package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsSpuSpecDao;
import com.billow.product.pojo.po.GoodsSpuSpecPo;
import com.billow.product.pojo.search.GoodsSpuSpecSearchParam;
import com.billow.product.service.GoodsSpuSpecService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * spu规格表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class GoodsSpuSpecServiceImpl extends HighLevelServiceImpl<GoodsSpuSpecDao, GoodsSpuSpecPo,GoodsSpuSpecSearchParam> implements GoodsSpuSpecService {

}


package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelV2ServiceImpl;
import com.billow.product.dao.GoodsVertifyRecordDao;
import com.billow.product.pojo.po.GoodsVertifyRecordPo;
import com.billow.product.pojo.search.GoodsVertifyRecordSearchParam;
import com.billow.product.service.GoodsVertifyRecordService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品审核记录表，用于记录商品审核记录 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class GoodsVertifyRecordServiceImpl extends HighLevelV2ServiceImpl<GoodsVertifyRecordDao, GoodsVertifyRecordPo,GoodsVertifyRecordSearchParam> implements GoodsVertifyRecordService {

}


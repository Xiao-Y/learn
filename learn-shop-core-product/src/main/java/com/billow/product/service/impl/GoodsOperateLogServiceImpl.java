package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsOperateLogDao;
import com.billow.product.pojo.search.GoodsOperateLogSearchParam;
import com.billow.product.pojo.po.GoodsOperateLogPo;
import com.billow.product.service.GoodsOperateLogService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品操作记录表，用于记录商品操作记录

 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class GoodsOperateLogServiceImpl extends HighLevelServiceImpl<GoodsOperateLogDao, GoodsOperateLogPo,GoodsOperateLogSearchParam> implements GoodsOperateLogService {

}


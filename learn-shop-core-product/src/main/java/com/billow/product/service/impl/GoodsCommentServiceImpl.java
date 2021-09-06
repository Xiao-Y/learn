package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsCommentDao;
import com.billow.product.pojo.po.GoodsCommentPo;
import com.billow.product.pojo.search.GoodsCommentSearchParam;
import com.billow.product.service.GoodsCommentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class GoodsCommentServiceImpl extends HighLevelServiceImpl<GoodsCommentDao, GoodsCommentPo,GoodsCommentSearchParam> implements GoodsCommentService {

}


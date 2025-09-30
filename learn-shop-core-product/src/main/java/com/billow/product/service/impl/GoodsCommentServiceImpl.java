package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelV2ServiceImpl;
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
public class GoodsCommentServiceImpl extends HighLevelV2ServiceImpl<GoodsCommentDao, GoodsCommentPo,GoodsCommentSearchParam> implements GoodsCommentService {

}


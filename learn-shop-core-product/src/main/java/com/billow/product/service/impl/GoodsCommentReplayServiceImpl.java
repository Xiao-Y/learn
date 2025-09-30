package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.GoodsCommentReplayDao;
import com.billow.product.pojo.search.GoodsCommentReplaySearchParam;
import com.billow.product.pojo.po.GoodsCommentReplayPo;
import com.billow.product.service.GoodsCommentReplayService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 产品评价回复表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class GoodsCommentReplayServiceImpl extends HighLevelServiceImpl<GoodsCommentReplayDao, GoodsCommentReplayPo,GoodsCommentReplaySearchParam> implements GoodsCommentReplayService {

}


package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelV2ServiceImpl;
import com.billow.product.dao.GoodsSafeguardDao;
import com.billow.product.pojo.search.GoodsSafeguardSearchParam;
import com.billow.product.pojo.po.GoodsSafeguardPo;
import com.billow.product.service.GoodsSafeguardService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 增值保障 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class GoodsSafeguardServiceImpl extends HighLevelV2ServiceImpl<GoodsSafeguardDao, GoodsSafeguardPo,GoodsSafeguardSearchParam> implements GoodsSafeguardService {

}


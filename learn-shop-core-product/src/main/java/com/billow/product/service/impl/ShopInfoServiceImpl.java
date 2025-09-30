package com.billow.product.service.impl;

import com.billow.mybatis.base.HighLevelServiceImpl;
import com.billow.product.dao.ShopInfoDao;
import com.billow.product.pojo.po.ShopInfoPo;
import com.billow.product.pojo.search.ShopInfoSearchParam;
import com.billow.product.service.ShopInfoService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 店铺表 服务实现类
 * </p>
 *
 * @author billow
 * @version v2.0
 * @since 2021-09-02
 */
@Service
public class ShopInfoServiceImpl extends HighLevelServiceImpl<ShopInfoDao, ShopInfoPo,ShopInfoSearchParam> implements ShopInfoService {

}


package com.billow.search.service;

import com.billow.search.dao.GoodsInfoDao;
import com.billow.search.pojo.po.GoodsInfoPo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * 商品服务类
 *
 * @author liuyongtao
 * @since 2021-9-2 20:04
 */
@Service
public class GoodsInfoServiceImpl implements GoodsInfoService {

    private static final int size = 3000;

    @Autowired
    private GoodsInfoDao goodsInfoDao;

    @Override
    public GoodsInfoPo getById(Long id) {
        return goodsInfoDao.findById(id).orElse(new GoodsInfoPo());
    }

    @Override
    public void saveOrUpdate(GoodsInfoPo goodsInfoPo) {
        goodsInfoDao.save(goodsInfoPo);
    }

    @Override
    public Page<GoodsInfoPo> findIdByPage(GoodsInfoPo goodsInfoPo) {
        return goodsInfoDao.searchSimilar(goodsInfoPo, new String[]{"id"}, PageRequest.of(0, size));
    }

    @Override
    public void delById(Long id) {
        goodsInfoDao.deleteById(id);
    }

}

package com.billow.product.service.impl;

import com.billow.product.service.ImportJdGoodsDetailService;
import com.billow.product.service.ImportJdGoodsInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImportJdGoodsInfoImpl implements ImportJdGoodsInfo
{
    @Autowired
    private ImportJdGoodsDetailService importJdGoodsDetailService;

    @Override
    public boolean importJdGoods(String keyword)
    {
        String spu = "";
        importJdGoodsDetailService.importGoodsSku(spu);
        return false;
    }
}

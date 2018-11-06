package com.billow.user.service.impl;


import com.billow.user.dao.CityDao;
import com.billow.user.pojo.po.CityPo;
import com.billow.user.service.CityService;
import com.billow.tools.utlis.ConvertUtils;
import com.billow.user.pojo.vo.CityVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liuyongtao
 * @create 2018-02-08 10:44
 */
@Service
public class CityServiceImpl implements CityService {

    @Autowired
    private CityDao cityDao;

    @Override
    public List<CityVo> findAll(CityVo cityVo) {
        List<CityPo> cityPos = cityDao.findAll();
        List<CityVo> cityVos = ConvertUtils.convert(cityPos, CityVo.class);
        return cityVos;
    }
}

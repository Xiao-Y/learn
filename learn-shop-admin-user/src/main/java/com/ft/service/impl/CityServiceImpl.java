package com.ft.service.impl;


import com.ft.dao.CityDao;
import com.ft.po.CityPo;
import com.ft.service.CityService;
import com.ft.utlis.BeanUtils;
import com.ft.vo.CityVo;
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
        List<CityVo> cityVos = BeanUtils.convert(cityPos, CityVo.class);
        return cityVos;
    }
}

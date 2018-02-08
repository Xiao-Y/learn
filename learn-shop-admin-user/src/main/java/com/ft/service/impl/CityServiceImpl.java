package com.ft.service.impl;

import com.ft.dao.CityRepository;
import com.ft.model.CityModel;
import com.ft.service.CityService;
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
    private CityRepository cityRepository;

    @Override
    public List<CityModel> findAll(CityModel cityModel) {
        return cityRepository.findAll();
    }
}

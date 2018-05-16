package com.billow.service;

import com.billow.pojo.vo.CityVo;

import java.util.List;

public interface CityService {

    List<CityVo> findAll(CityVo CityVo);
}

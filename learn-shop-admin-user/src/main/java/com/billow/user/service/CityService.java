package com.billow.user.service;

import com.billow.user.pojo.vo.CityVo;

import java.util.List;

public interface CityService {

    List<CityVo> findAll(CityVo CityVo);
}

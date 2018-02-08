package com.ft.service;

import com.ft.model.CityModel;

import java.util.List;

public interface CityService {
    List<CityModel> findAll(CityModel cityModel);
}

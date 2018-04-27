package com.ft.dao;

import com.ft.po.CityPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<CityPo, Long> {
}

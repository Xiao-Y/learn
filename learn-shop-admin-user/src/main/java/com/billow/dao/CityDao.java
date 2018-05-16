package com.billow.dao;

import com.billow.pojo.po.CityPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<CityPo, Long> {
}

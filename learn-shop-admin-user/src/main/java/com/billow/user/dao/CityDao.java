package com.billow.user.dao;

import com.billow.user.pojo.po.CityPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityDao extends JpaRepository<CityPo, Long> {
}

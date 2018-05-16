package com.billow.dao;

import com.billow.pojo.po.TestPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDao extends JpaRepository<TestPo, Long> {
}

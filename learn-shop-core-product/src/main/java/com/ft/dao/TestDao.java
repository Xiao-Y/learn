package com.ft.dao;

import com.ft.po.TestPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDao extends JpaRepository<TestPo, Long> {
}

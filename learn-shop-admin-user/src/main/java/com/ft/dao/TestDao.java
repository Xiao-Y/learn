package com.ft.dao;

import com.ft.po.TestPo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 测试用
 *
 * @author liuyongtao
 * @create 2018-02-11 9:43
 */
public interface TestDao extends JpaRepository<TestPo, Integer> {
}

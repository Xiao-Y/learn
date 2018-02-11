package com.ft.dao;

import com.ft.model.TestModel;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 测试用
 *
 * @author liuyongtao
 * @create 2018-02-11 9:43
 */
public interface TestRepository extends JpaRepository<TestModel, Integer> {
}

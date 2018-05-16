package com.billow.dao;

import com.billow.pojo.po.OrderPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<OrderPo, Integer> {
}

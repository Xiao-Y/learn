package com.ft.dao;

import com.ft.po.OrderPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<OrderPo, Integer> {
}

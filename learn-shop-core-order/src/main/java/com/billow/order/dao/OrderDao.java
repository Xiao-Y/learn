package com.billow.order.dao;

import com.billow.order.pojo.po.OrderPo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<OrderPo, Long> {
}

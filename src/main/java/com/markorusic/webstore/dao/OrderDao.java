package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDao extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}

package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDetailDao extends JpaRepository<OrderDetail, Long> {
}

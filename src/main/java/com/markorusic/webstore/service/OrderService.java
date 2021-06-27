package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Order;
import com.markorusic.webstore.domain.OrderStatus;
import com.markorusic.webstore.dto.order.OrderRequestDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<Order> findAll(Predicate predicate, Pageable pageable);

    Order save(OrderRequestDto orderRequestDto);

    List<Order> findCustomerOrders();

    Order findById(Long id);

    Order cancelOrder(Long id);

    Order changeStatus(Long id, OrderStatus status);
}

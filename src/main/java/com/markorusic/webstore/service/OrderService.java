package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.OrderStatus;
import com.markorusic.webstore.dto.order.OrderDto;
import com.markorusic.webstore.dto.order.OrderPageItemDto;
import com.markorusic.webstore.dto.order.OrderRequestDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    Page<OrderPageItemDto> findAll(Predicate predicate, Pageable pageable);

    OrderDto save(OrderRequestDto orderRequestDto);

    List<OrderDto> findCustomerOrders();

    OrderDto changeStatus(Long orderId, OrderStatus status);

    OrderDto findById(Long id);
}

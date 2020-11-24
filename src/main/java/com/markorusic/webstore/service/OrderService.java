package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.OrderStatus;
import com.markorusic.webstore.dto.order.OrderDto;
import com.markorusic.webstore.dto.order.OrderRequestDto;

import java.util.List;

public interface OrderService {
    OrderDto save(OrderRequestDto orderRequestDto);

    List<OrderDto> findCustomerOrders();

    OrderDto changeStatus(Long orderId, OrderStatus status);
}

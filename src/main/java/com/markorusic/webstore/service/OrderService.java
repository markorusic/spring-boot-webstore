package com.markorusic.webstore.service;

import com.markorusic.webstore.dto.OrderDto;
import com.markorusic.webstore.dto.OrderRequestDto;

import java.util.List;

public interface OrderService {

    OrderDto save(OrderRequestDto orderRequestDto);

    List<OrderDto> findCustomerOrders();

}

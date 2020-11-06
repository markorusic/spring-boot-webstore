package com.markorusic.webstore.controller;

import com.markorusic.webstore.dto.OrderDto;
import com.markorusic.webstore.dto.OrderRequestDto;
import com.markorusic.webstore.service.OrderService;
import com.markorusic.webstore.util.ValidationGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "Order Api")
@RequestMapping(path = "/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Method for creating new order")
    public OrderDto save(@Validated(ValidationGroup.Save.class) @RequestBody OrderRequestDto orderRequestDto) {
        return orderService.save(orderRequestDto);
    }

    @RequestMapping(value = "/findByCustomerId", method = RequestMethod.GET)
    @ApiOperation(value = "Method for finding customer's orders")
    public List<OrderDto> findCustomerOrders() {
        return orderService.findCustomerOrders();
    }
}

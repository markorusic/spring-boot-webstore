package com.markorusic.webstore.controller;

import com.markorusic.webstore.dao.OrderDao;
import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Order;
import com.markorusic.webstore.domain.OrderStatus;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.order.OrderDto;
import com.markorusic.webstore.dto.order.OrderPageItemDto;
import com.markorusic.webstore.dto.order.OrderRequestDto;
import com.markorusic.webstore.service.OrderService;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
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

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting all orders with pagination and search support")
    public Page<OrderPageItemDto> findAll(@QuerydslPredicate(root = Order.class, bindings = OrderDao.class) Predicate predicate, Pageable pageable) {
        return orderService.findAll(predicate, pageable);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ApiOperation(value = "Method for search single order with all details by id")
    public OrderDto findById(@RequestParam Long id) {
        return orderService.findById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Method for creating new order")
    public OrderDto save(@Validated @RequestBody OrderRequestDto orderRequestDto) {
        return orderService.save(orderRequestDto);
    }

    @RequestMapping(value = "/me", method = RequestMethod.GET)
    @ApiOperation(value = "Method for finding currently authenticated customer's orders")
    public List<OrderDto> findCustomerOrders() {
        return orderService.findCustomerOrders();
    }

    @RequestMapping(value = "/cancel", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for canceling order")
    public OrderDto cancelOrder(@RequestParam Long id) {
        return orderService.changeStatus(id, OrderStatus.Canceled);
    }

    @RequestMapping(value = "/ship", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for shipping order")
    public OrderDto shipOrder(@RequestParam Long id) {
        return orderService.changeStatus(id, OrderStatus.Shipped);
    }
}

package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.OrderDao;
import com.markorusic.webstore.dao.OrderDetailDao;
import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Order;
import com.markorusic.webstore.domain.OrderDetail;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.OrderDto;
import com.markorusic.webstore.dto.OrderRequestDto;
import com.markorusic.webstore.service.AuthService;
import com.markorusic.webstore.service.OrderService;
import com.markorusic.webstore.util.exception.SafeModeException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AuthService authService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ModelMapper mapper;

    @Override
    public OrderDto save(OrderRequestDto orderRequestDto) {
        var productIds = orderRequestDto.getOrderDetails().stream().map(orderDetailRequestDto -> orderDetailRequestDto.getProductId()).collect(Collectors.toList());
        var products = productDao.findByIdIn(productIds);

        if (products.isEmpty()) {
            throw new SafeModeException("Order needs to have at least 1 valid product.");
        }

        var productsMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        var order = Order.builder()
                .customer(authService.getCustomer())
                .note(orderRequestDto.getNote())
                .shippingAddress(orderRequestDto.getShippingAddress())
                .build();

        orderDao.save(order);

        var orderDetails = orderRequestDto.getOrderDetails()
                .stream()
                .map(orderDetailRequestDto -> {
                    var product = productsMap.get(orderDetailRequestDto.getProductId());
                    return  OrderDetail
                            .builder()
                            .quantity(orderDetailRequestDto.getQuantity())
                            .name(product.getName())
                            .photo(product.getPhoto())
                            .productId(product.getId())
                            .price(product.getPrice())
                            .order(order)
                            .build();
                        }
                )
                .collect(Collectors.toList());


        orderDetailDao.saveAll(orderDetails);
        order.setOrderDetails(orderDetails);

        return mapper.map(order, OrderDto.class);
    }

    @Override
    public List<OrderDto> findCustomerOrders() {
        var customerId = authService.getCustomer().getId();
        return orderDao.findByCustomerId(customerId)
                .stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }
}

package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.OrderDao;
import com.markorusic.webstore.dao.OrderDetailDao;
import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Order;
import com.markorusic.webstore.domain.OrderDetail;
import com.markorusic.webstore.domain.OrderStatus;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.order.OrderDto;
import com.markorusic.webstore.dto.order.OrderPageItemDto;
import com.markorusic.webstore.dto.order.OrderRequestDto;
import com.markorusic.webstore.dto.product.ProductDto;
import com.markorusic.webstore.security.AuthService;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.service.OrderService;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.markorusic.webstore.util.exception.SafeModeException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AuthService authService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderDao orderDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Page<OrderPageItemDto> findAll(Predicate predicate, Pageable pageable) {
        var orders = productDao.findAll(new BooleanBuilder().and(predicate), pageable);
        return new PageImpl<>(orders.stream()
                .map(order -> mapper.map(order, OrderPageItemDto.class))
                .collect(Collectors.toList()), pageable, orders.getTotalElements());
    }

    @Override
    public OrderDto save(OrderRequestDto orderRequestDto) {
        var productIds = orderRequestDto.getOrderDetails().stream().map(orderDetailRequestDto -> orderDetailRequestDto.getProductId()).collect(Collectors.toList());
        var products = productDao.findByIdIn(productIds);

        if (productIds.size() != products.size()) {
            throw new SafeModeException("Cannot process invalid products.");
        }

        var productsMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        var customer = customerService.getAuthenticatedCustomer();
        var order = Order.builder()
                .customer(customer)
                .note(orderRequestDto.getNote())
                .shippingAddress(orderRequestDto.getShippingAddress())
                .status(OrderStatus.Pending)
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

        var orderDto = mapper.map(order, OrderDto.class);
        customerService.track("Created order with id" + order.getId());

        return orderDto;
    }

    @Override
    public List<OrderDto> findCustomerOrders() {
        var customerId = authService.getUser().getId();
        return orderDao.findByCustomerId(customerId)
                .stream()
                .map(order -> mapper.map(order, OrderDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public OrderDto changeStatus(Long orderId, OrderStatus status) {
        var order = orderDao.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with identifier %s not found!", orderId.toString())));
        order.setStatus(status);
        orderDao.save(order);
        return mapper.map(order, OrderDto.class);
    }

    @Override
    public OrderDto findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        var order = orderDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with identifier %s not found!", id.toString())));
        return mapper.map(order, OrderDto.class);
    }
}

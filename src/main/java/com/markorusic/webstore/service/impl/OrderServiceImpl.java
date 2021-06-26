package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.OrderDao;
import com.markorusic.webstore.dao.OrderDetailDao;
import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Order;
import com.markorusic.webstore.domain.OrderDetail;
import com.markorusic.webstore.domain.OrderStatus;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.order.OrderDetailRequestDto;
import com.markorusic.webstore.dto.order.OrderRequestDto;
import com.markorusic.webstore.security.AuthService;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.service.OrderService;
import com.markorusic.webstore.util.exception.BadRequestException;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.markorusic.webstore.util.exception.SafeModeException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final AuthService authService;

    private final CustomerService customerService;

    private final OrderDao orderDao;

    private final OrderDetailDao orderDetailDao;

    private final ProductDao productDao;

    @Override
    public Page<Order> findAll(Predicate predicate, Pageable pageable) {
        return orderDao.findAll(new BooleanBuilder().and(predicate), pageable);
    }

    @Override
    public Order save(OrderRequestDto orderRequestDto) {
        if (orderRequestDto.getOrderDetails().size() == 0) {
            throw new BadRequestException("Order has no items.");
        }
        var productIds = orderRequestDto.getOrderDetails().stream().map(OrderDetailRequestDto::getProductId).collect(Collectors.toList());
        var products = productDao.findByIdIn(productIds);

        if (productIds.size() != products.size()) {
            throw new BadRequestException("Cannot process invalid products.");
        }

        var productsMap = products.stream()
                .collect(Collectors.toMap(Product::getId, product -> product));

        var customer = customerService.getAuthenticatedCustomer();
        var order = Order.builder()
                .customer(customer)
                .note(orderRequestDto.getNote())
                .shippingAddress(orderRequestDto.getShippingAddress())
                .status(OrderStatus.Pending)
                .createdAt(LocalDateTime.now())
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

        return order;
    }

    @Override
    public List<Order> findCustomerOrders() {
        var customerId = authService.getUser().getId();
        return orderDao.findByCustomerId(customerId);
    }


    @Override
    public Order findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        return orderDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Order with identifier %s not found!", id)));
    }

    @Override
    public Order cancelOrder(Long id) {
        var order = findById(id);
        if (order.getStatus() == OrderStatus.Shipped) {
            throw new SafeModeException("Cannot cancel shipped order");
        }
        order.withStatus(OrderStatus.Canceled);
        orderDao.save(order);
        return order;
    }

    @Override
    public Order shipOrder(Long id) {
        var order = findById(id);
        if (order.getStatus() == OrderStatus.Canceled) {
            throw new SafeModeException("Cannot ship canceled order");
        }
        order.withStatus(OrderStatus.Shipped);
        orderDao.save(order);
        return order;
    }
}

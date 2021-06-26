package com.markorusic.webstore.dto.order;

import com.markorusic.webstore.domain.OrderStatus;
import com.markorusic.webstore.dto.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderPageItemDto {
    private Long id;

    private String shippingAddress;

    private String note;

    private OrderStatus status;

    private LocalDateTime createdAt;

    private CustomerDto customer;

}

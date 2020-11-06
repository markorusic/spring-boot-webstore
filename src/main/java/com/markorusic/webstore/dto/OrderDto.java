package com.markorusic.webstore.dto;

import com.markorusic.webstore.domain.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDto {
    private Long id;

    private String shippingAddress;

    private String note;

    private CustomerDto customer;

    private List<OrderDetailDto> orderDetails;
}

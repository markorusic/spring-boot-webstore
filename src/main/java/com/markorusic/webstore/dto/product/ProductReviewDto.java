package com.markorusic.webstore.dto.product;

import com.markorusic.webstore.dto.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDto {
    private Long id;

    private Float rating;

    private String content;

    private Long productId;

    private CustomerDto customer;
}

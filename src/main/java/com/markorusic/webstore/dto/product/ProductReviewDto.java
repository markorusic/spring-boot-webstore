package com.markorusic.webstore.dto.product;

import com.markorusic.webstore.dto.customer.CustomerDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewDto {
    private Long id;

    private Float rate;

    private String content;

    private LocalDateTime updatedAt;

    private Long productId;

    private CustomerDto customer;
}

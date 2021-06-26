package com.markorusic.webstore.dto.customer;

import com.markorusic.webstore.dto.product.ProductReviewDto;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerProductInfoDto {
    private Boolean canReview;

    private ProductReviewDto review;
}

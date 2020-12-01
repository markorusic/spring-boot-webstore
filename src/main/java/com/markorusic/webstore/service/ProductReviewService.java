package com.markorusic.webstore.service;

import com.markorusic.webstore.dto.product.ProductReviewDto;
import com.markorusic.webstore.dto.product.ProductReviewRequestDto;
import org.springframework.data.domain.Page;

public interface ProductReviewService {
    ProductReviewDto save(ProductReviewRequestDto productReviewRequestDto);

    ProductReviewDto update(ProductReviewRequestDto productReviewRequestDto);

    void delete(Long id);

    Page<ProductReviewDto> findByProductId(Long productId);
}

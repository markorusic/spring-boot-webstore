package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.ProductReview;
import com.markorusic.webstore.dto.product.ProductReviewRequestDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductReviewService {
    ProductReview save(ProductReviewRequestDto productReviewRequestDto);

    ProductReview update(ProductReviewRequestDto productReviewRequestDto);

    Page<ProductReview> findByProductId(Long productId, Pageable pageable);

    void delete(Long id);

    List<ProductReview> findCustomerReviews();
}

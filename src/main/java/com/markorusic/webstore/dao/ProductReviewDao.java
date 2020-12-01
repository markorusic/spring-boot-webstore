package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.ProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReviewDao extends JpaRepository<ProductReview, Long> {
    Page<ProductReview> findByProductId(Long productId, Pageable pageable);

    List<ProductReview> findByProductIdAndCustomerId(Long productId, Long customerId);
}

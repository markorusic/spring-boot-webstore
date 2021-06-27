package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.ProductReview;
import com.markorusic.webstore.domain.QProductReview;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;

import java.util.List;

public interface ProductReviewDao extends JpaRepository<ProductReview, Long>, QuerydslPredicateExecutor<ProductReview>, QuerydslBinderCustomizer<QProductReview> {
    Page<ProductReview> findByProductId(Long productId, Pageable pageable);

    List<ProductReview> findByCustomerId(Long productId);

    List<ProductReview> findByProductIdAndCustomerId(Long productId, Long customerId);
}

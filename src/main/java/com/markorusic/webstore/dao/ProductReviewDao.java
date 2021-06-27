package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.ProductReview;
import com.markorusic.webstore.domain.QProduct;
import com.markorusic.webstore.domain.QProductReview;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringPath;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.querydsl.binding.SingleValueBinding;

import java.util.List;

public interface ProductReviewDao extends JpaRepository<ProductReview, Long>, QuerydslPredicateExecutor<ProductReview>, QuerydslBinderCustomizer<QProductReview> {
    Page<ProductReview> findByProductId(Long productId, Pageable pageable);

    List<ProductReview> findByCustomerId(Long productId);

    List<ProductReview> findByProductIdAndCustomerId(Long productId, Long customerId);

    @Override
    default void customize(QuerydslBindings bindings, QProductReview root) {
        bindings.bind(String.class)
                .first((SingleValueBinding<StringPath, String>) StringExpression::containsIgnoreCase);
    }
}

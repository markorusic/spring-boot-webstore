package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.ProductReview;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductReviewDao extends JpaRepository<ProductReview, Long> {
}

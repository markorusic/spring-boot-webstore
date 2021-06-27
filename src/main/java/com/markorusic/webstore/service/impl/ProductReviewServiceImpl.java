package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.ProductReviewDao;
import com.markorusic.webstore.domain.ProductReview;
import com.markorusic.webstore.dto.product.ProductReviewDto;
import com.markorusic.webstore.dto.product.ProductReviewRequestDto;
import com.markorusic.webstore.security.exception.ForbiddenException;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.service.ProductReviewService;
import com.markorusic.webstore.service.ProductService;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.markorusic.webstore.util.exception.SafeModeException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewDao productReviewDao;

    private final CustomerService customerService;

    private final ProductService productService;

    private ProductReview findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        return productReviewDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product review with identifier %s not found!", id)));
    }

    @Override
    public ProductReview save(ProductReviewRequestDto productReviewRequestDto) {
        var product = productService.findById(productReviewRequestDto.getProductId());
        var customer = customerService.getAuthenticatedCustomer();
        var existingReviews = productReviewDao.findByProductIdAndCustomerId(product.getId(), customer.getId());
        if (existingReviews.size() != 0) {
            throw new SafeModeException("Cannot have more than one review per product");
        }
        var review = ProductReview.builder()
                .content(productReviewRequestDto.getContent())
                .rate(productReviewRequestDto.getRate())
                .customer(customer)
                .product(product)
                .updatedAt(LocalDateTime.now())
                .build();
        productReviewDao.save(review);
        return review;
    }

    @Override
    public ProductReview update(ProductReviewRequestDto productReviewRequestDto) {
        var review = findById(productReviewRequestDto.getId())
            .withContent(productReviewRequestDto.getContent())
            .withRate(productReviewRequestDto.getRate())
            .withUpdatedAt(LocalDateTime.now());
        var customer = customerService.getAuthenticatedCustomer();
        if (!review.getCustomer().getId().equals(customer.getId())) {
            throw new ForbiddenException("Cannot update other customer's reviews");
        }
        productReviewDao.save(review);
        return review;
    }

    @Override
    public void delete(Long id) {
        var review = findById(id);
        var customer = customerService.getAuthenticatedCustomer();
        if (!review.getCustomer().getId().equals(customer.getId())) {
            throw new ForbiddenException("Cannot update other customer's reviews");
        }
        productReviewDao.delete(review);
    }

    @Override
    public List<ProductReview> findCustomerReviews() {
        var customer = customerService.getAuthenticatedCustomer();
        return productReviewDao.findByCustomerId(customer.getId());
    }

    @Override
    public Page<ProductReview> findAll(Predicate predicate, Pageable pageable) {
        return productReviewDao.findAll(new BooleanBuilder().and(predicate), pageable);
    }

    @Override
    public Page<ProductReview> findByProductId(Long productId, Pageable pageable) {
        return productReviewDao.findByProductId(productId, pageable);
    }
}

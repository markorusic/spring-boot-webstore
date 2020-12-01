package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.ProductReviewDao;
import com.markorusic.webstore.domain.ProductReview;
import com.markorusic.webstore.dto.product.ProductReviewRequestDto;
import com.markorusic.webstore.security.exception.ForbiddenException;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.service.ProductReviewService;
import com.markorusic.webstore.service.ProductService;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.markorusic.webstore.util.exception.SafeModeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    private ProductReviewDao productReviewDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

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
                .build();
        productReviewDao.save(review);
        return review;
    }

    @Override
    public ProductReview update(ProductReviewRequestDto productReviewRequestDto) {
        var review = findById(productReviewRequestDto.getId())
            .withContent(productReviewRequestDto.getContent())
            .withRate(productReviewRequestDto.getRate());
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
    public Page<ProductReview> findByProductId(Long productId, Pageable pageable) {
        return productReviewDao.findByProductId(productId, pageable);
    }
}

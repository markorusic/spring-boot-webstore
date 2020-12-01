package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.ProductReviewDao;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.domain.ProductReview;
import com.markorusic.webstore.domain.QCustomerAction;
import com.markorusic.webstore.dto.customer.CustomerActionDto;
import com.markorusic.webstore.dto.product.ProductReviewDto;
import com.markorusic.webstore.dto.product.ProductReviewRequestDto;
import com.markorusic.webstore.security.exception.ForbiddenException;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.service.ProductReviewService;
import com.markorusic.webstore.service.ProductService;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.markorusic.webstore.util.exception.SafeModeException;
import com.querydsl.core.BooleanBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    @Autowired
    private ProductReviewDao productReviewDao;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ModelMapper mapper;

    private ProductReview findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        return productReviewDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product review with identifier %s not found!", id.toString())));
    }

    @Override
    public ProductReviewDto save(ProductReviewRequestDto productReviewRequestDto) {
        var productDto = productService.findById(productReviewRequestDto.getProductId());
        var product = mapper.map(productDto, Product.class);
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
        customerService.track(String.format("Created review with id %s of product with id %s", review.getId(), product.getId()));
        return mapper.map(review, ProductReviewDto.class);
    }

    @Override
    public ProductReviewDto update(ProductReviewRequestDto productReviewRequestDto) {
        var review = findById(productReviewRequestDto.getId())
            .withContent(productReviewRequestDto.getContent())
            .withRate(productReviewRequestDto.getRate());
        var customer = customerService.getAuthenticatedCustomer();
        if (review.getCustomer().getId() != customer.getId()) {
            throw new ForbiddenException("Cannot update other customer's reviews");
        }
        productReviewDao.save(review);
        customerService.track(String.format("Updated review with id %s of product with id %s", review.getId(), review.getProduct().getId()));
        return mapper.map(review, ProductReviewDto.class);
    }

    @Override
    public void delete(Long id) {
        var review = findById(id);
        var customer = customerService.getAuthenticatedCustomer();
        var productId = review.getProduct().getId();
        if (review.getCustomer().getId() != customer.getId()) {
            throw new ForbiddenException("Cannot update other customer's reviews");
        }
        productReviewDao.delete(review);
        customerService.track(String.format("Deleted review with id %s of product with id %s", id, productId));
    }

    @Override
    public Page<ProductReviewDto> findByProductId(Long productId, Pageable pageable) {
        var reviews = productReviewDao.findByProductId(productId, pageable);
        return new PageImpl<>(reviews.stream()
                .map(review -> mapper.map(review, ProductReviewDto.class))
                .collect(Collectors.toList()), pageable, reviews.getTotalElements());
    }
}

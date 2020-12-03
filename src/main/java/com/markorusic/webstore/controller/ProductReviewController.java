package com.markorusic.webstore.controller;

import com.markorusic.webstore.dto.product.ProductReviewDto;
import com.markorusic.webstore.dto.product.ProductReviewRequestDto;
import com.markorusic.webstore.service.CustomerService;
import com.markorusic.webstore.service.ProductReviewService;
import com.markorusic.webstore.util.MappingUtils;
import com.markorusic.webstore.util.validation.ValidationGroup;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Customer Api")
@RequestMapping(path = "/product-reviews")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    private final CustomerService customerService;

    private final MappingUtils mapper;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Method for creating new product review")
    public ProductReviewDto save(@Validated(ValidationGroup.Save.class) @RequestBody ProductReviewRequestDto productReviewRequestDto) {
        var review = productReviewService.save(productReviewRequestDto);
        customerService.track(String.format("Created review with id %s of product with id %s", review.getId(), productReviewRequestDto.getProductId()));
        return mapper.map(review, ProductReviewDto.class);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for updating existing product review")
    public ProductReviewDto update(@Validated(ValidationGroup.Update.class) @RequestBody ProductReviewRequestDto productReviewRequestDto) {
        var review = productReviewService.update(productReviewRequestDto);
        customerService.track(String.format("Updated review with id %s of product with id %s", review.getId(), review.getProduct().getId()));
        return mapper.map(review, ProductReviewDto.class);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Method for deleting existing product review")
    public void delete(@RequestParam Long id) {
        productReviewService.delete(id);
        customerService.track(String.format("Deleted product review with id %s", id));
    }

    @RequestMapping(value = "/product/findById", method = RequestMethod.GET)
    @ApiOperation(value = "Method for finding product reviews of specific product")
    public Page<ProductReviewDto> findByProductId(@RequestParam Long id, Pageable pageable) {
        var reviews = productReviewService.findByProductId(id, pageable);
        return mapper.mapPage(reviews, ProductReviewDto.class, pageable);
    }

}

package com.markorusic.webstore.controller;

import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.product.ProductPageItemDto;
import com.markorusic.webstore.dto.product.ProductRequestDto;
import com.markorusic.webstore.dto.product.ProductDto;
import com.markorusic.webstore.service.ProductService;
import com.markorusic.webstore.util.validation.ValidationGroup;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Api")
@RequestMapping(path = "/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting all products with pagination and search support")
    public Page<ProductPageItemDto> findAll(@QuerydslPredicate(root = Product.class, bindings = ProductDao.class) Predicate predicate, Pageable pageable) {
        return productService.findAll(predicate, pageable);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ApiOperation(value = "Method for search single product with all details by id")
    public ProductDto findById(@RequestParam Long id) {
        return productService.findById(id);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Method for creating new product")
    public ProductDto save(@Validated(ValidationGroup.Save.class) @RequestBody ProductRequestDto productRequestDto) {
        return productService.save(productRequestDto);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for updating existing product")
    public ProductDto update(@Validated(ValidationGroup.Update.class) @RequestBody ProductRequestDto productRequestDto) {
        return productService.update(productRequestDto);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Method for deleting existing product")
    public void delete(@RequestParam Long id) {
        productService.delete(id);
    }
}

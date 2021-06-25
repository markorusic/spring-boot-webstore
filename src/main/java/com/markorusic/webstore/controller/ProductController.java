package com.markorusic.webstore.controller;

import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.product.ProductDto;
import com.markorusic.webstore.dto.product.ProductPageItemDto;
import com.markorusic.webstore.dto.product.ProductRequestDto;
import com.markorusic.webstore.service.AdminService;
import com.markorusic.webstore.service.ProductService;
import com.markorusic.webstore.util.MappingUtils;
import com.markorusic.webstore.util.validation.ValidationGroup;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Api(value = "Product Api")
@RequestMapping(path = "/products")
public class ProductController {

    private final ProductService productService;

    private final AdminService adminService;

    private final MappingUtils mapper;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting all products with pagination and search support")
    public Page<ProductPageItemDto> findAll(@QuerydslPredicate(root = Product.class, bindings = ProductDao.class) Predicate predicate, Pageable pageable) {
        var products = productService.findAll(predicate, pageable);
        return mapper.mapPage(products, ProductPageItemDto.class, pageable);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ApiOperation(value = "Method for search single product with all details by id")
    public ProductDto findById(@RequestParam Long id) {
        var product = productService.findById(id);
        return mapper.map(product, ProductDto.class);
    }

    @RequestMapping(value = "/findByIds", method = RequestMethod.GET)
    @ApiOperation(value = "Method for search multiple products with basic details by ids")
    public List<ProductPageItemDto> findById(@RequestParam List<Long> ids) {
        var products = productService.findByIds(ids);
        return mapper.mapList(products, ProductPageItemDto.class);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Method for creating new product")
    public ProductDto save(@Validated(ValidationGroup.Save.class) @RequestBody ProductRequestDto productRequestDto) {
        var product = productService.save(productRequestDto);
        adminService.track("Created product with id " + product.getId());
        return mapper.map(product, ProductDto.class);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for updating existing product")
    public ProductDto update(@Validated(ValidationGroup.Update.class) @RequestBody ProductRequestDto productRequestDto) {
        var product = productService.update(productRequestDto);
        adminService.track("Updated product with id " + product.getId());
        return mapper.map(product, ProductDto.class);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Method for deleting existing product")
    public void delete(@RequestParam Long id) {
        productService.delete(id);
        adminService.track("Deleted product with id " + id);
    }
}

package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.ProductRequestDto;
import com.markorusic.webstore.dto.ProductResponseDto;
import com.markorusic.webstore.service.ProductService;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Page<Product> findAll(Predicate predicate, Pageable pageable) {
        Page<Product> products = productDao.findAll(new BooleanBuilder().and(predicate), pageable);
        return new PageImpl<>(products.stream()
                .collect(Collectors.toList()), pageable, products.getTotalElements());
    }

    @Override
    public ProductResponseDto findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        var product = productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with identifier %s not found!", id.toString())));
        return mapper.map(product, ProductResponseDto.class);
    }

    @Override
    public Product save(ProductRequestDto productRequestDto) {
        var product = Product.builder()
            .name(productRequestDto.getName())
            .price(productRequestDto.getPrice())
            .build();
        productDao.save(product);
        return product;
    }

    @Override
    public Product update(ProductRequestDto productRequestDto) {
        var productDto = findById(productRequestDto.getId());
        var product = mapper.map(productDto, Product.class)
            .withName(productRequestDto.getName())
            .withPrice(productRequestDto.getPrice());
        productDao.save(product);
        return product;
    }
}

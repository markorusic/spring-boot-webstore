package com.markorusic.webstore.service;

import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.ProductRequestDto;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public Page<Product> findAll(Predicate predicate, Pageable pageable) {
        Page<Product> products = productDao.findAll(new BooleanBuilder().and(predicate), pageable);
        return new PageImpl<>(products.stream()
                .collect(Collectors.toList()), pageable, products.getTotalElements());
    }

    @Override
    public Product findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        return productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with identifier %s not found!", id.toString())));
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
        var product = findById(productRequestDto.getId())
            .withName(productRequestDto.getName())
            .withPrice(productRequestDto.getPrice());
        productDao.save(product);
        return product;
    }
}

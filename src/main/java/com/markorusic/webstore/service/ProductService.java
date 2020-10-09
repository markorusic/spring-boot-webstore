package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.ProductRequestDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {
    Page<Product> findAll(Predicate predicate, Pageable pageable);

    Product findById(Long id);

    Product save(ProductRequestDto productRequestDto);

    Product update(ProductRequestDto productRequestDto);
}

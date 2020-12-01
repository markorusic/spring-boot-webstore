package com.markorusic.webstore.service;

import com.markorusic.webstore.dto.product.ProductPageItemDto;
import com.markorusic.webstore.dto.product.ProductRequestDto;
import com.markorusic.webstore.dto.product.ProductDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductPageItemDto> findAll(Predicate predicate, Pageable pageable);

    ProductDto findById(Long id);

    ProductDto save(ProductRequestDto productRequestDto);

    ProductDto update(ProductRequestDto productRequestDto);

    void delete(Long id);
}

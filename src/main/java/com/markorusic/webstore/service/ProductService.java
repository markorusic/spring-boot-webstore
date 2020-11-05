package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.ProductPageDto;
import com.markorusic.webstore.dto.ProductRequestDto;
import com.markorusic.webstore.dto.ProductDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ProductService {
    Page<ProductPageDto> findAll(Predicate predicate, Pageable pageable);

    ProductDto findById(Long id);

    ProductDto save(ProductRequestDto productRequestDto);

    ProductDto update(ProductRequestDto productRequestDto);

    void delete(Long id);
}

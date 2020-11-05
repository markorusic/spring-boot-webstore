package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.*;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<CategoryPageDto> findAll(Predicate predicate, Pageable pageable);

    CategoryDto findById(Long id);

    CategoryDto save(CategoryRequestDto categoryRequestDto);

    CategoryDto update(CategoryRequestDto categoryRequestDto);

    void delete(Long id);
}

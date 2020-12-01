package com.markorusic.webstore.service;

import com.markorusic.webstore.domain.Category;
import com.markorusic.webstore.dto.category.CategoryRequestDto;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CategoryService {
    Page<Category> findAll(Predicate predicate, Pageable pageable);

    Category findById(Long id);

    Category save(CategoryRequestDto categoryRequestDto);

    Category update(CategoryRequestDto categoryRequestDto);

    void delete(Long id);
}

package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dto.CategoryDto;
import com.markorusic.webstore.dto.CategoryPageDto;
import com.markorusic.webstore.dto.CategoryRequestDto;
import com.markorusic.webstore.service.CategoryService;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Override
    public Page<CategoryPageDto> findAll(Predicate predicate, Pageable pageable) {
        return null;
    }

    @Override
    public CategoryDto findById(Long id) {
        return null;
    }

    @Override
    public CategoryDto save(CategoryRequestDto categoryRequestDto) {
        return null;
    }

    @Override
    public CategoryDto update(CategoryRequestDto categoryRequestDto) {
        return null;
    }
}

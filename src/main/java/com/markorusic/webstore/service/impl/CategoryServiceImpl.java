package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.CategoryDao;
import com.markorusic.webstore.domain.Category;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.dto.*;
import com.markorusic.webstore.service.CategoryService;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryDao categoryDao;

    @Autowired
    ModelMapper mapper;

    @Override
    public Page<CategoryPageDto> findAll(Predicate predicate, Pageable pageable) {
        var categories = categoryDao.findAll(new BooleanBuilder().and(predicate), pageable);
        return new PageImpl<>(categories.stream()
                .map(category -> mapper.map(category, CategoryPageDto.class))
                .collect(Collectors.toList()), pageable, categories.getTotalElements());
    }

    @Override
    public CategoryDto findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        var category = categoryDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Categroy with identifier %s not found!", id.toString())));
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto save(CategoryRequestDto categoryRequestDto) {
        var category = Category.builder()
                .name(categoryRequestDto.getName())
                .build();
        categoryDao.save(category);
        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryRequestDto categoryRequestDto) {
        var categoryDto = findById(categoryRequestDto.getId());
        var category = mapper.map(categoryDto, Category.class)
                .withName(categoryRequestDto.getName());
        categoryDao.save(category);
        return mapper.map(category, CategoryDto.class);
    }
}

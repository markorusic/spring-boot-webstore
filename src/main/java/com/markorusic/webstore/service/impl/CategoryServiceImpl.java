package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.CategoryDao;
import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.domain.Category;
import com.markorusic.webstore.dto.category.CategoryRequestDto;
import com.markorusic.webstore.service.CategoryService;
import com.markorusic.webstore.util.MappingUtils;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.markorusic.webstore.util.exception.SafeModeException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;


@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryDao categoryDao;

    @Autowired
    private ProductDao  productDao;

    @Autowired
    private MappingUtils mappingUtils;

    @Override
    public Page<Category> findAll(Predicate predicate, Pageable pageable) {
        return categoryDao.findAll(new BooleanBuilder().and(predicate), pageable);
    }

    @Override
    public Category findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        return categoryDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Category with identifier %s not found!", id)));
    }

    @Override
    public Category save(CategoryRequestDto categoryRequestDto) {
        var category = Category.builder()
                .name(categoryRequestDto.getName())
                .build();
        categoryDao.save(category);
        return category;
    }

    @Override
    public Category update(CategoryRequestDto categoryRequestDto) {
        var categoryDto = findById(categoryRequestDto.getId());
        var category = mappingUtils.map(categoryDto, Category.class)
                .withName(categoryRequestDto.getName());
        categoryDao.save(category);
        return category;
    }

    @Override
    public void delete(Long id) {
        var category = findById(id);
        if(productDao.existsByCategoryId(id)) {
            throw new SafeModeException("Cannot delete category that has products in it.");
        }
        categoryDao.delete(category);
    }
}

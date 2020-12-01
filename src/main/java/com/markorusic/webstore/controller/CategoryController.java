package com.markorusic.webstore.controller;

import com.markorusic.webstore.dao.CategoryDao;
import com.markorusic.webstore.domain.Category;
import com.markorusic.webstore.dto.category.CategoryDto;
import com.markorusic.webstore.dto.category.CategoryPageDto;
import com.markorusic.webstore.dto.category.CategoryRequestDto;
import com.markorusic.webstore.service.AdminService;
import com.markorusic.webstore.service.CategoryService;
import com.markorusic.webstore.util.MappingUtils;
import com.markorusic.webstore.util.validation.ValidationGroup;
import com.querydsl.core.types.Predicate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.querydsl.binding.QuerydslPredicate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Api(value = "Category Api")
@RequestMapping(path = "/categories")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private MappingUtils mapper;

    @Autowired
    private AdminService adminService;

    @RequestMapping(value = "/findAll", method = RequestMethod.GET)
    @ApiOperation(value = "Method for getting all categories with pagination and search support")
    public Page<CategoryPageDto> findAll(@QuerydslPredicate(root = Category.class, bindings = CategoryDao.class) Predicate predicate, Pageable pageable) {
        var categories = categoryService.findAll(predicate, pageable);
        return mapper.mapPage(categories, CategoryPageDto.class, pageable);
    }

    @RequestMapping(value = "/findById", method = RequestMethod.GET)
    @ApiOperation(value = "Method for finding single category with all details by id")
    public CategoryDto findById(@RequestParam Long id) {
        var category = categoryService.findById(id);
        return mapper.map(category, CategoryDto.class);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "Method for creating new category")
    public CategoryDto save(@Validated(ValidationGroup.Save.class) @RequestBody CategoryRequestDto categoryRequestDto) {
        var category = categoryService.save(categoryRequestDto);
        adminService.track("Created category with id " + category.getId());
        return mapper.map(category, CategoryDto.class);
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    @ApiOperation(value = "Method for updating existing category")
    public CategoryDto update(@Validated(ValidationGroup.Update.class) @RequestBody CategoryRequestDto categoryRequestDto) {
        var category = categoryService.update(categoryRequestDto);
        adminService.track("Updated category with id " + category.getId());
        return mapper.map(category, CategoryDto.class);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE)
    @ApiOperation(value = "Method for deleting existing category")
    public void delete(@RequestParam Long id) {
        categoryService.delete(id);
        adminService.track("Deleted category with id " + id);
    }
}

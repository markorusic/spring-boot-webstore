package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.dao.ProductPhotoDao;
import com.markorusic.webstore.domain.Category;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.domain.ProductPhoto;
import com.markorusic.webstore.dto.product.ProductPageDto;
import com.markorusic.webstore.dto.product.ProductRequestDto;
import com.markorusic.webstore.dto.product.ProductDto;
import com.markorusic.webstore.service.CategoryService;
import com.markorusic.webstore.service.ProductService;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ProductDao productDao;

    @Autowired
    private ProductPhotoDao productPhotoDao;

    @Autowired
    private CategoryService categoryService;

    @Override
    public Page<ProductPageDto> findAll(Predicate predicate, Pageable pageable) {
        var products = productDao.findAll(new BooleanBuilder().and(predicate), pageable);
        return new PageImpl<>(products.stream()
                .map(product -> mapper.map(product, ProductPageDto.class))
                .collect(Collectors.toList()), pageable, products.getTotalElements());
    }

    @Override
    public ProductDto findById(Long id) {
        Assert.notNull(id, "Parameter can't by null!");
        var product = productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with identifier %s not found!", id.toString())));
        return mapper.map(product, ProductDto.class);
    }

    private Product prepareProduct(Product product, ProductRequestDto productRequestDto) {
        var categoryDto = categoryService.findById((productRequestDto.getCategoryId()));
        var category = mapper.map(categoryDto, Category.class);
        return product
                .withName(productRequestDto.getName())
                .withPrice(productRequestDto.getPrice())
                .withPhoto(productRequestDto.getPhoto())
                .withCategory(category);
    }

    @Transactional
    private void savePhotos(Product product, ProductRequestDto productRequestDto) {
        var photos = productRequestDto
                .getPhotos()
                .stream()
                .map(photo ->ProductPhoto.builder().path(photo).product(product).build())
                .collect(Collectors.toList());
        if (productRequestDto.getId() != null) {
            productPhotoDao.deleteByProductId(productRequestDto.getId());
        }
        productPhotoDao.saveAll(photos);
        product.setPhotos(photos);
    }

    @Override
    @Transactional
    public ProductDto save(ProductRequestDto productRequestDto) {
        var product = prepareProduct(new Product(), productRequestDto);
        productDao.save(product);
        savePhotos(product, productRequestDto);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    @Transactional
    public ProductDto update(ProductRequestDto productRequestDto) {
        var productDto = findById(productRequestDto.getId());
        var product = prepareProduct(mapper.map(productDto, Product.class), productRequestDto);
        productDao.save(product);
        savePhotos(product, productRequestDto);
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public void delete(Long id) {
        var productDto = findById(id);
        var product = mapper.map(productDto, Product.class);
        productDao.delete(product);
    }
}

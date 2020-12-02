package com.markorusic.webstore.service.impl;

import com.markorusic.webstore.dao.ProductDao;
import com.markorusic.webstore.dao.ProductPhotoDao;
import com.markorusic.webstore.domain.Product;
import com.markorusic.webstore.domain.ProductPhoto;
import com.markorusic.webstore.dto.product.ProductRequestDto;
import com.markorusic.webstore.service.CategoryService;
import com.markorusic.webstore.service.ProductService;
import com.markorusic.webstore.util.exception.ResourceNotFoundException;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import javax.transaction.Transactional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;

    private final ProductPhotoDao productPhotoDao;

    private final CategoryService categoryService;

    @Override
    public Page<Product> findAll(Predicate predicate, Pageable pageable) {
        return productDao.findAll(new BooleanBuilder().and(predicate), pageable);
    }

    @Override
    public Product findById(Long id) {
        Assert.notNull(id, "Product id can't by null!");
        return productDao.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Product with identifier %s not found!", id)));
    }

    private Product prepareProduct(Product product, ProductRequestDto productRequestDto) {
        var category = categoryService.findById((productRequestDto.getCategoryId()));
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
    public Product save(ProductRequestDto productRequestDto) {
        var product = prepareProduct(new Product(), productRequestDto);
        productDao.save(product);
        savePhotos(product, productRequestDto);
        return product;
    }

    @Override
    @Transactional
    public Product update(ProductRequestDto productRequestDto) {
        var existingProduct = findById(productRequestDto.getId());
        var product = prepareProduct(existingProduct, productRequestDto);
        productDao.save(product);
        savePhotos(product, productRequestDto);
        return product;
    }

    @Override
    public void delete(Long id) {
        var product = findById(id);
        productDao.delete(product);
    }
}

package com.markorusic.webstore.dao;

import com.markorusic.webstore.domain.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductPhotoDao extends JpaRepository<ProductPhoto, Long> {
    void deleteByProductId(Long id);
}

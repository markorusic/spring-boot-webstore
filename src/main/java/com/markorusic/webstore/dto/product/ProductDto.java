package com.markorusic.webstore.dto.product;

import com.markorusic.webstore.dto.category.CategoryPageDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private Long id;

    private String name;

    private Float price;

    private String photo;

    private List<ProductPhotoDto> photos;

    private CategoryPageDto category;

}
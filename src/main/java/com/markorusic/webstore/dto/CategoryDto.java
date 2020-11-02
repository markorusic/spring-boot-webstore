package com.markorusic.webstore.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {

    private Long id;

    private String name;

    private List<ProductDto> products;

}

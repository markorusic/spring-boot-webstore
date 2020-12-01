package com.markorusic.webstore.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageItemDto {
    private Long id;

    private String name;

    private Float price;

    private String photo;

    private Long categoryId;
}

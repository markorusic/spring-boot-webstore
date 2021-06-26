package com.markorusic.webstore.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductPageItemDto {
    private Long id;

    private String name;

    private Float price;

    private String photo;

    private Long categoryId;

    private LocalDateTime createdAt;
}

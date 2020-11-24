package com.markorusic.webstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {
    private Long id;

    private String name;

    private Float price;

    private String photo;

    private Long quantity;

    private Long productId;
}

package com.markorusic.webstore.dto.category;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryPageDto {
    private Long id;

    private String name;

    private String photo;
}

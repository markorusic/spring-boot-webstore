package com.markorusic.webstore.dto;

import com.markorusic.webstore.util.exception.ValidationGroup;
import lombok.*;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotNull(groups = ValidationGroup.Update.class, message = "ID cannot be null")
    private Long id;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "price cannot be null")
    private float price;
}

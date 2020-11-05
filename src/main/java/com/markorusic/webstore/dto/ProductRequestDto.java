package com.markorusic.webstore.dto;

import com.markorusic.webstore.util.ValidationGroup;
import lombok.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotNull(groups = ValidationGroup.Update.class, message = "ID cannot be null")
    private Long id;

    @NotNull(message = "name cannot be null")
    private String name;

    @NotNull(message = "price cannot be null")
    private Float price;

    @NotNull(message = "photo cannot be null")
    private String photo;

    @NotNull(message = "categoryId cannot be null")
    private Long categoryId;

    @NotNull(message = "photos cannot be null")
    @Size(min = 3)
    private List<String> photos;
}

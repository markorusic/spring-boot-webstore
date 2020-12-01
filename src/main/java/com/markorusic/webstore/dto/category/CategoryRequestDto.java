package com.markorusic.webstore.dto.category;

import com.markorusic.webstore.util.validation.ValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequestDto {

    @NotNull(groups = ValidationGroup.Update.class, message = "ID cannot be null")
    private Long id;

    @NotNull(message = "name cannot be null")
    private String name;

}

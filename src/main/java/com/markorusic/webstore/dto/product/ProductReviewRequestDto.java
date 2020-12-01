package com.markorusic.webstore.dto.product;

import com.markorusic.webstore.util.validation.ValidationGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductReviewRequestDto {

    @NotNull(groups = ValidationGroup.Update.class, message = "ID cannot be null")
    private Long id;

    @NotNull
    @Min(1)
    @Max(10)
    private Float rating;

    @NotNull
    private String content;

    @NotNull
    private Long productId;
}

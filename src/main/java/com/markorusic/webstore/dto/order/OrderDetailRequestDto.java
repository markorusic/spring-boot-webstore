package com.markorusic.webstore.dto.order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailRequestDto {

    @NotNull
    @Min(1)
    private Long quantity;

    @NotNull
    private Long productId;
}
